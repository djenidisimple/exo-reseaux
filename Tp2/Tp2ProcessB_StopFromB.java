import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * ProcessB_StopFromB - Variante où B contrôle l'arrêt
 * Reçoit et affiche les chaînes transmises par ProcessA
 * Utilise 2 threads :
 *   - Thread 1 : reçoit les messages de A
 *   - Thread 2 : lit au clavier pour détecter "stop"
 * S'arrête et notifie A lorsque B lit "stop"
 * Protocole : B envoie un message de terminaison "STOP_FROM_B" à A.
 * A reçoit ce signal et termine.
 */
public class Tp2ProcessB_StopFromB {
    static volatile boolean shouldStop = false;
    static PrintStream out;
    
    public static void main(String[] args) {
        try {
            // Création d'un serveur écoutant sur le port 1027
            ServerSocket serverSocket = new ServerSocket(1027);
            System.out.println("ProcessB en attente de connexion sur le port 1027...");
            
            // Accepter la connexion de ProcessA
            Socket socket = serverSocket.accept();
            System.out.println("ProcessA connecté.");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            
            // Thread 1 : recevoir les messages de A
            Thread receiverThread = new Thread(() -> {
                try {
                    String received;
                    while (!shouldStop && (received = in.readLine()) != null) {
                        System.out.println("Reçu de A : " + received);
                    }
                } catch (IOException e) {
                    if (!shouldStop) {
                        System.err.println("Erreur lecture : " + e.getMessage());
                    }
                }
            });
            
            // Thread 2 : lire au clavier
            Thread keyboardThread = new Thread(() -> {
                Scanner keyboard = new Scanner(System.in);
                String input;
                while (!shouldStop) {
                    System.out.print("Entrer 'stop' pour arrêter : ");
                    if (keyboard.hasNextLine()) {
                        input = keyboard.nextLine();
                        if (input.equalsIgnoreCase("stop")) {
                            shouldStop = true;
                            // Envoyer le signal d'arrêt à A
                            out.println("STOP_FROM_B");
                            out.flush();
                            System.out.println("Signal d'arrêt envoyé à ProcessA");
                        }
                    }
                }
                keyboard.close();
            });
            
            receiverThread.start();
            keyboardThread.start();
            
            // Attendre la fin des threads
            receiverThread.join();
            keyboardThread.join();
            
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
            System.out.println("Arrêt de ProcessB");
            
        } catch (IOException | InterruptedException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}
