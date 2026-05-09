import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * ProcessA - Lit des chaînes au clavier et les transmet à ProcessB via socket TCP
 * Port: 1027
 * Arrêt: Lorsque A lit "stop" au clavier
 * Protocole de terminaison : A envoie "stop" à B, puis ferme la socket.
 * B lit "stop", affiche le message, puis termine.
 */
public class Tp2ProcessA {
    public static void main(String[] args) {
        try {
            // Connexion au serveur B
            Socket socket = new Socket("localhost", 1027);
            PrintStream out = new PrintStream(socket.getOutputStream());
            Scanner keyboard = new Scanner(System.in);
            
            String input;
            boolean stop = false;
            
            while (!stop) {
                System.out.print("Entrer une chaîne (ou 'stop' pour arrêter) : ");
                input = keyboard.nextLine();
                
                // Envoyer la chaîne à B
                out.println(input);
                out.flush();
                
                if (input.equalsIgnoreCase("stop")) {
                    stop = true;
                    System.out.println("Arrêt de ProcessA");
                }
            }
            
            out.close();
            socket.close();
            keyboard.close();
            
        } catch (IOException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
