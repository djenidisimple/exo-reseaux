import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client TCP pour TP3.
 *
 * - Se connecte au serveur TCP sur le port 1027
 * - Envoie des chaînes lues au clavier
 * - Affiche toutes les chaînes reçues du serveur
 */
public class Tp3TCPClient {
    private static final int PORT = 1027;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";

        try (Socket socket = new Socket(host, PORT);
             Scanner keyboard = new Scanner(System.in);
             PrintStream out = new PrintStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connecté au serveur TCP " + host + ":" + PORT);

            Thread readerThread = new Thread(() -> {
                try {
                    String serverLine;
                    while ((serverLine = in.readLine()) != null) {
                        System.out.println(serverLine);
                    }
                } catch (IOException e) {
                    System.err.println("Lecture interrompue : " + e.getMessage());
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();

            while (keyboard.hasNextLine()) {
                String line = keyboard.nextLine();
                out.println(line);
                out.flush();
                if (line.equalsIgnoreCase("stop")) {
                    break;
                }
            }

            System.out.println("Déconnexion du client TCP.");
        } catch (IOException e) {
            System.err.println("Erreur client TCP : " + e.getMessage());
        }
    }
}
