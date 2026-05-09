package Tp4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

public class Tp4TCPPeriodicServer {
    private static final int PORT = 1027;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur périodique TCP démarré sur le port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new PeriodicClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur TCP : " + e.getMessage());
        }
    }

    private static class PeriodicClientHandler implements Runnable {
        private final Socket socket;
        private final AtomicBoolean stopped = new AtomicBoolean(false);

        PeriodicClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintStream out = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(socket.getOutputStream());

                String request = reader.readLine();
                if (request != null && !request.isEmpty()) {
                    System.out.println("Requête reçue de " + socket.getRemoteSocketAddress() + " : " + request);

                    Thread stopListener = new Thread(() -> {
                        try {
                            String clientMessage;
                            while (!stopped.get() && (clientMessage = reader.readLine()) != null) {
                                if (clientMessage.equalsIgnoreCase("stop")) {
                                    stopped.set(true);
                                    break;
                                }
                            }
                        } catch (IOException ignored) {
                        }
                    });
                    stopListener.setDaemon(true);
                    stopListener.start();

                    while (!stopped.get() && !socket.isClosed()) {
                        String message = "Message périodique du serveur : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
                        out.println(message);
                        out.flush();

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ignored) {
                        }
                    }

                    if (stopped.get()) {
                        out.println("Fin de la diffusion demandée par le client.");
                        out.flush();
                    }
                }
            } catch (IOException e) {
                System.err.println("Connexion interrompue avec " + socket.getRemoteSocketAddress());
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
