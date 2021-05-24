package pl.dele;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the path to the directory to be sorted: ");
        String directoryPath = "";

        while (directoryPath.isBlank()) {
            if (scanner.hasNextLine()) {
                directoryPath = scanner.nextLine();
            }
        }
        scanner.close();

        CatalogAnalyzer analyzer = new CatalogAnalyzer(directoryPath);
        analyzer.analysis();

        System.out.println("The jpg files in the \"" + directoryPath + "\" are sorted");
    }

}
