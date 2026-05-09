import java.util.Scanner;

/**
 * Main.java - Interface principale pour découvrir les TP de réseaux
 * Permet à l'utilisateur de choisir quel TP explorer via un menu interactif
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        System.out.println("=== TP Réseaux - Menu Principal ===");
        System.out.println("Bienvenue dans l'interface de découverte des TP !");
        System.out.println();

        while (continuer) {
            afficherMenu();
            System.out.print("Votre choix : ");
            String choix = scanner.nextLine().trim();

            switch (choix.toLowerCase()) {
                case "1":
                    presenterTP1();
                    break;
                case "2":
                    presenterTP2();
                    break;
                case "3":
                    presenterTP3();
                    break;
                case "4":
                    presenterTP4();
                    break;
                case "stop":
                case "quit":
                case "q":
                    continuer = false;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("=== Menu des TP ===");
        System.out.println("1. TP1 - Introduction aux sockets TCP");
        System.out.println("2. TP2 - Communication TCP entre processus");
        System.out.println("3. TP3 - Client/Serveur TCP avec threads");
        System.out.println("4. TP4 - InetAddress, Socket et Thread");
        System.out.println("stop - Quitter");
        System.out.println("===================");
    }

    private static void presenterTP1() {
        System.out.println("=== TP1 - Introduction aux sockets TCP ===");
        System.out.println("Ce TP n'est pas encore implémenté dans ce projet.");
        System.out.println("Il devrait couvrir les bases des sockets TCP en Java.");
    }

    private static void presenterTP2() {
        System.out.println("=== TP2 - Communication TCP entre processus ===");
        System.out.println("Ce TP couvre la communication TCP entre deux processus A et B.");
        System.out.println();
        System.out.println("Contenu du TP2 :");
        System.out.println("1a) Lecture de chaînes au clavier avec Scanner (Tp2A1.java)");
        System.out.println("1b) Copie de fichier avec PrintStream (Tp2B1.java)");
        System.out.println("2b) Communication TCP même machine (Tp2ProcessA.java + Tp2ProcessB.java)");
        System.out.println("2c) Communication TCP machines différentes (Tp2ProcessA_Remote.java + Tp2ProcessB_Remote.java)");
        System.out.println("2d-e) Arrêt contrôlé par B avec threads (Tp2ProcessA_StopFromB.java + Tp2ProcessB_StopFromB.java)");
        System.out.println();
        System.out.println("Pour tester :");
        System.out.println("- Compilez : javac Tp2/*.java");
        System.out.println("- Lancez les processus dans des terminaux séparés");
        System.out.println("- Exemple : java Tp2.Tp2ProcessB & java Tp2.Tp2ProcessA");
    }

    private static void presenterTP3() {
        System.out.println("=== TP3 - Client/Serveur TCP avec threads ===");
        System.out.println("Ce TP couvre les applications client/serveur TCP avec gestion multi-clients.");
        System.out.println();
        System.out.println("Contenu du TP3 :");
        System.out.println("- Serveur TCP actif (Tp3TCPServer.java)");
        System.out.println("- Client TCP (Tp3TCPClient.java)");
        System.out.println("- Serveur UDP (Tp3UDPServer.java)");
        System.out.println("- Client UDP (Tp3UDPClient.java)");
        System.out.println();
        System.out.println("Pour tester :");
        System.out.println("- Compilez : javac Tp3/*.java");
        System.out.println("- Lancez le serveur : java Tp3.Tp3TCPServer");
        System.out.println("- Lancez le client : java Tp3.Tp3TCPClient");
    }

    private static void presenterTP4() {
        System.out.println("=== TP4 - InetAddress, Socket et Thread ===");
        System.out.println("Ce TP couvre l'utilisation d'InetAddress, Socket et Thread pour les réseaux.");
        System.out.println();
        System.out.println("Contenu du TP4 :");
        System.out.println("1) Résolution d'adresses IP (Tp4InetAddressLookup.java)");
        System.out.println("2) Détection de machines actives TCP (Tp4TCPActiveServer.java + Tp4TCPActiveClient.java)");
        System.out.println("3) Communication périodique TCP (Tp4TCPPeriodicServer.java + Tp4TCPPeriodicClient.java)");
        System.out.println("4) Architecture proxy local (Tp4LocalProxyServer.java + Tp4LocalProxyClient.java)");
        System.out.println();
        System.out.println("Pour tester :");
        System.out.println("- Compilez : javac Tp4/*.java");
        System.out.println("- Exemple résolution IP : java Tp4.Tp4InetAddressLookup");
        System.out.println("- Exemple détection machines : java Tp4.Tp4TCPActiveServer & java Tp4.Tp4TCPActiveClient");
    }
}