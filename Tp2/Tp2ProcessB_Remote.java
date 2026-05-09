import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ProcessB_Remote - Pour machines différentes
 * S'exécute sur le serveur et écoute sur le port 1027
 * Accepte les connexions de ProcessA_Remote depuis une autre machine
 * Protocole : identique à la version même machine, mais A se connecte
 * à l'adresse IP distante de B.
 */
public class Tp2ProcessB_Remote {
    public static void main(String[] args) {
        int port = 1027;
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("ProcessB en attente de connexion sur le port " + port);
            System.out.println("Adresse locale : " + serverSocket.getInetAddress());
            
            Socket socket = serverSocket.accept();
            System.out.println("ProcessA connecté depuis " + socket.getInetAddress());
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String received;
            boolean stop = false;
            
            while (!stop) {
                received = in.readLine();
                
                if (received == null) {
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
