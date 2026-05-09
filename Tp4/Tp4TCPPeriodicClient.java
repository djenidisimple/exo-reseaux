package Tp4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Tp4TCPPeriodicClient {
    private static final int PORT = 1027;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket(host, PORT);
             PrintStream out = new PrintStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connecté au serveur périodique TCP " + host + ":" + PORT);
            System.out.print("Entrez la requête initiale : ");
            String request = scanner.nextLine();
            out.println(request);
            out.flush();

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

            while (true) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("stop")) {
                    out.println("stop");
                    out.flush();
                    break;
                }
            }

            System.out.println("Client périodique TCP terminé.");
        } catch (IOException e) {
            System.err.println("Erreur client TCP : " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
