import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serveur TCP pour TP3.
 *
 * - Écoute le port 1027
 * - Reçoit des chaînes de chaque client
 * - Redistribue chaque chaîne à tous les clients connectés
 */
public class Tp3TCPServer {
    private static final int PORT = 1027;
    private static final List<PrintStream> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur TCP démarré sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté : " + clientSocket.getRemoteSocketAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur TCP : " + e.getMessage());
        }
    }

    private static void broadcast(String message) {
        synchronized (clients) {
            for (PrintStream client : new ArrayList<>(clients)) {
                client.println(message);
                client.flush();
                if (client.checkError()) {
                    clients.remove(client);
                }
            }
        }
    }

    private static void removeClient(PrintStream client) {
        synchronized (clients) {
            clients.remove(client);
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;
        private PrintStream writer;

        private ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                writer = new PrintStream(socket.getOutputStream());
                synchronized (clients) {
                    clients.add(writer);
                }

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Message reçu de " + socket.getRemoteSocketAddress() + " : " + line);
                    broadcast("Client " + socket.getRemoteSocketAddress() + " -> " + line);
                }
            } catch (IOException e) {
                System.err.println("Connexion perdue avec " + socket.getRemoteSocketAddress());
            } finally {
                if (writer != null) {
                    removeClient(writer);
                }
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
                System.out.println("Client déconnecté : " + socket.getRemoteSocketAddress());
            }
        }
    }
}
