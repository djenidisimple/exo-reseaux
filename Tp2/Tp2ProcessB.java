import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ProcessB - Reçoit et affiche les chaînes transmises par ProcessA via socket TCP
 * Port: 1027
 * Arrêt: Lorsque B reçoit "stop" de la part de A
 * Protocole de terminaison : B attend les lignes de A, affiche chaque ligne.
 * Quand il reçoit "stop", il termine proprement.
 */
public class Tp2ProcessB {
    public static void main(String[] args) {
        try {
            // Création d'un serveur écoutant sur le port 1027
            ServerSocket serverSocket = new ServerSocket(1027);
            System.out.println("ProcessB en attente de connexion sur le port 1027...");
            
            // Accepter la connexion de ProcessA
            Socket socket = serverSocket.accept();
            System.out.println("ProcessA connecté.");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String received;
            boolean stop = false;
            
            while (!stop) {
                received = in.readLine();
                
                if (received == null) {
                    // Connexion fermée
                    break;
                }
                
                System.out.println("Reçu : " + received);
                
                if (received.equalsIgnoreCase("stop")) {
                    stop = true;
                    System.out.println("Arrêt de ProcessB");
                }
            }
            
            in.close();
            socket.close();
            serverSocket.close();
            
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}
