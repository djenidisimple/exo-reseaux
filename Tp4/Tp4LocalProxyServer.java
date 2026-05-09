package Tp4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Tp4LocalProxyServer {
    private static final int PORT = 1026;
    private static final int REMOTE_PORT = 1027;

    public static void main(String[] args) {
        System.out.println("Proxy local démarré sur le port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ProxyHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur proxy : " + e.getMessage());
        }
    }

    private static class ProxyHandler implements Runnable {
        private final Socket clientSocket;

        ProxyHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintStream out = new PrintStream(clientSocket.getOutputStream())) {

                String request = in.readLine();
                if (request != null && !request.isEmpty()) {
                    System.out.println("Requête proxy : " + request + " de " + clientSocket.getRemoteSocketAddress());
                if (request.startsWith("CHECK ")) {
                    String host = request.substring(6).trim();
                    String response = checkHost(host);
                    out.println(response);
                } else if (request.startsWith("SCAN ")) {
                    String argument = request.substring(5).trim();
                    String host = argument;
                    if (argument.endsWith("-25")) {
                        host = argument.substring(0, argument.length() - 3).trim();
                    }
                    List<String> results = scanHosts(host, 25);
                    for (String line : results) {
                        out.println(line);
                    }
                } else {
                    out.println("Syntaxe invalide. Utilisez CHECK <hôte> ou SCAN <hôte>.");
                }
                out.println("END");
                }
            } catch (IOException e) {
                System.err.println("Erreur handler proxy : " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static String checkHost(String host) {
        try (Socket remote = new Socket(host, REMOTE_PORT);
             PrintStream out = new PrintStream(remote.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()))) {
            out.println("PING");
            out.flush();
            String response = in.readLine();
            return "OK " + host + " -> " + (response == null ? "pas de réponse" : response);
        } catch (IOException e) {
            return "KO " + host + " -> machine inactive ou inaccessible";
        }
    }

    private static List<String> scanHosts(String startHost, int count) {
        List<String> results = new ArrayList<>();
        try {
            InetAddress base = InetAddress.getByName(startHost);
            byte[] bytes = base.getAddress();
            for (int i = 0; i < count; i++) {
                int last = Byte.toUnsignedInt(bytes[3]) + i;
                if (last > 255) {
                    results.add("Fin de plage atteinte.");
                    break;
                }
                byte[] currentBytes = bytes.clone();
                currentBytes[3] = (byte) last;
                InetAddress current = InetAddress.getByAddress(currentBytes);
                results.add(checkHost(current.getHostAddress()));
            }
        } catch (IOException e) {
            results.add("Adresse de départ invalide : " + startHost);
        }
        return results;
    }
}
