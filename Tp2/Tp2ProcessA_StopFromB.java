import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * ProcessA_StopFromB - Variante où B contrôle l'arrêt
 * Lit des chaînes au clavier et les transmet à ProcessB
 * S'arrête lorsque B envoie le signal "STOP_FROM_B"
 * Protocole : B lit "stop" au clavier, puis envoie un message de terminaison à A.
 * A reçoit le signal et termine proprement.
 */
public class Tp2ProcessA_StopFromB {
    static volatile boolean shouldStop = false;
    
    public static void main(String[] args) {
        try {
            // Connexion au serveur B
            Socket socket = new Socket("localhost", 1027);
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner keyboard = new Scanner(System.in);
            
            // Thread pour lire les réponses de B (et le signal d'arrêt)
            Thread receiverThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        if (response.equalsIgnoreCase("STOP_FROM_B")) {
                            System.out.println("Signal d'arrêt reçu de B");
                            shouldStop = true;
                            break;
                        } else {
                            System.out.println("Réponse de B : " + response);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Erreur lecture : " + e.getMessage());
                }
            });
            
            receiverThread.start();
            
            // Thread principal pour lire au clavier
            String input;
            while (!shouldStop) {
                System.out.print("Entrer une chaîne : ");
                if (keyboard.hasNextLine()) {
                    input = keyboard.nextLine();
                    out.println(input);
                    out.flush();
                }
            }
            
            out.close();
            socket.close();
            keyboard.close();
            System.out.println("Arrêt de ProcessA");
            
        } catch (IOException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
