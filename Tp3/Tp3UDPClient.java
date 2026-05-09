import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Client UDP pour TP3.
 *
 * - Envoie des chaînes lues au clavier au serveur UDP sur le port 9876
 */
public class Tp3UDPClient {
    private static final int PORT = 9876;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";

        try (DatagramSocket socket = new DatagramSocket();
             Scanner keyboard = new Scanner(System.in)) {

            InetAddress serverAddress = InetAddress.getByName(host);
            System.out.println("Client UDP prêt, envoi vers " + host + ":" + PORT);

            while (keyboard.hasNextLine()) {
                String message = keyboard.nextLine();
                byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, PORT);
                socket.send(packet);

                if (message.equalsIgnoreCase("stop")) {
                    break;
                }
            }

            System.out.println("Client UDP terminé.");
        } catch (IOException e) {
            System.err.println("Erreur client UDP : " + e.getMessage());
        }
    }
}
