import java.util.Scanner;

public class Tp2A1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Entrer une chaîne (ou 'stop' pour arrêter) : ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("stop")) {
                System.out.println("Programme arrêté.");
                break;
            }

            System.out.println("Vous avez entré : " + input);
        }

        scanner.close();
    }
}
