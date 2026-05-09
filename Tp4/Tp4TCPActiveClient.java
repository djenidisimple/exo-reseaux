package Tp4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Tp4TCPActiveClient {
    private static final int PORT = 1027;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Client TCP: tester une machine ou 25 machines à partir d'une adresse.");
        System.out.print("Entrez une adresse IP unique ou une adresse de départ suivie de '-25' : ");
        String input = scanner.nextLine().trim();

        if (input.endsWith("-25")) {
            String baseAddress = input.substring(0, input.length() - 3).trim();
            testerPlage(baseAddress, 25);
        } else {
            testerUneMachine(input);
        }

        scanner.close();
    }

    private static void testerUneMachine(String host) {
        System.out.println("Test de la machine : " + host);
        envoyerRequete(host);
    }

    private static void testerPlage(String startHost, int count) {
        try {
            InetAddress startAddress = InetAddress.getByName(startHost);
            byte[] bytes = startAddress.getAddress();
            for (int i = 0; i < count; i++) {
                int last = Byte.toUnsignedInt(bytes[3]) + i;
                if (last > 255) {
                    System.out.println("Fin de plage atteinte.");
                    break;
                }
                byte[] currentBytes = bytes.clone();
                currentBytes[3] = (byte) last;
                InetAddress current = InetAddress.getByAddress(currentBytes);
                System.out.println("Test de " + current.getHostAddress());
                envoyerRequete(current.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.err.println("Adresse de départ invalide : " + startHost);
        }
    }

    private static void envoyerRequete(String host) {
        try (Socket socket = new Socket(host, PORT);
             PrintStream out = new PrintStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("PING");
            out.flush();
            String response = in.readLine();
            if (response != null) {
                System.out.println("Réponse de " + host + " -> " + response);
            }
        } catch (IOException e) {
            System.out.println("Machine non active ou inaccessible : " + host);
        }
    }
}
