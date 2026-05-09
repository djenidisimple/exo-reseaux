package Tp4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Tp4LocalProxyClient {
    private static final int PORT = 1026;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Client proxy local vers " + host + ":" + PORT);
        System.out.print("Entrez une requête (CHECK <hôte> ou SCAN <hôte>[, SCAN <hôte>-25]) : ");
        String request = scanner.nextLine();

        try (Socket socket = new Socket(host, PORT);
             PrintStream out = new PrintStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(request);
            out.flush();

            String response;
            while ((response = in.readLine()) != null) {
                if (response.equals("END")) {
                    break;
                }
                System.out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Erreur client proxy : " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
