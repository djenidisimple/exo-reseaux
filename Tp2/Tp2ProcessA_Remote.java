import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * ProcessA_Remote - Pour machines différentes
 * Modifiez "SERVER_IP" avec l'adresse IP réelle de la machine B
 * 
 * Exemple : String serverIP = "192.168.1.100"; (remplacer par l'IP réelle)
 * Protocole : identique à la version même machine, mais la connexion se fait
 * sur l'adresse IP distante de B au lieu de localhost.
 */
public class Tp2ProcessA_Remote {
    public static void main(String[] args) {
        // À modifier : mettre l'adresse IP de la machine où ProcessB s'exécute
        String serverIP = "localhost";  // Remplacer par l'IP réelle (ex: "192.168.1.100")
        int port = 1027;
        
        if (args.length > 0) {
            serverIP = args[0];  // Permet de passer l'IP en ligne de commande
        }
        
        try {
            System.out.println("Connexion à " + serverIP + ":" + port);
            Socket socket = new Socket(serverIP, port);
            PrintStream out = new PrintStream(socket.getOutputStream());
            Scanner keyboard = new Scanner(System.in);
            
            String input;
            boolean stop = false;
            
            while (!stop) {
                System.out.print("Entrer une chaîne (ou 'stop' pour arrêter) : ");
                input = keyboard.nextLine();
                
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
