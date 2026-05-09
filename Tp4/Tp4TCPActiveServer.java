package Tp4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tp4TCPActiveServer {
    private static final int PORT = 1027;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur TCP actif démarré sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur TCP : " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintStream out = new PrintStream(socket.getOutputStream())) {

                String request = reader.readLine();
                if (request != null && !request.isEmpty()) {
                    System.out.println("Requête reçue de " + socket.getRemoteSocketAddress() + " : " + request);
                    String response = "Machine active - heure locale : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    out.println(response);
                    out.flush();
                }
            } catch (IOException e) {
                System.err.println("Erreur client TCP : " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
