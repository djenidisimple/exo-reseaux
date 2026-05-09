import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Tp2B1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nom du fichier source (in) : ");
        String inputFile = scanner.nextLine();
        System.out.print("Nom du fichier destination (out) : ");
        String outputFile = scanner.nextLine();

        copierFichier(inputFile, outputFile);
        scanner.close();
    }

    private static void copierFichier(String inPath, String outPath) {
        try (FileInputStream fis = new FileInputStream(inPath);
             PrintStream writer = new PrintStream(new FileOutputStream(outPath))) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                writer.write(buffer, 0, bytesRead);
            }
            System.out.println("Copie terminée de " + inPath + " vers " + outPath + ".");
        } catch (IOException e) {
            System.err.println("Erreur de copie : " + e.getMessage());
        }
    }
}
