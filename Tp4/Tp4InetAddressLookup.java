package Tp4;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Tp4InetAddressLookup {
    public static void main(String[] args) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("Adresse locale : " + localHost.getHostAddress());
            System.out.println("Nom local : " + localHost.getHostName());
        } catch (UnknownHostException e) {
            System.err.println("Impossible de récupérer l'adresse locale : " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        String hostName;

        while (true) {
            System.out.print("Entrez un nom d'hôte (ou 'stop' pour quitter) : ");
            hostName = scanner.nextLine();

            if (hostName.equalsIgnoreCase("stop")) {
                System.out.println("Arrêt de l'application.");
                break;
            }

            try {
                InetAddress address = InetAddress.getByName(hostName);
                System.out.println("Adresse IP de " + hostName + " : " + address.getHostAddress());
            } catch (UnknownHostException e) {
                System.err.println("Hôte inconnu : " + hostName);
            }
        }

        scanner.close();
    }
}
