import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/**
 * Serveur UDP pour TP3.
 *
 * - Écoute le port 9876
 * - Affiche les chaînes reçues des clients
 */
public class Tp3UDPServer {
    private static final int PORT = 9876;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Serveur UDP démarré sur le port " + PORT);

            while (true) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                System.out.println("Reçu de " + packet.getAddress() + ":" + packet.getPort() + " -> " + message);

                if (message.equalsIgnoreCase("stop")) {
                    System.out.println("Arrêt serveur UDP demandé.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur serveur UDP : " + e.getMessage());
        }
    }
}
