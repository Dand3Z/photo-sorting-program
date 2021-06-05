package pl.dele;

import java.util.Scanner;

public class ConsoleVersionStart {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the path to the directory to be sorted: ");
        String sourceDirectoryPath = "";
        String destinationDirectoryPath = "";
        int threadAmount = -1;

        while (sourceDirectoryPath.isBlank()) {
            if (scanner.hasNextLine()) {
                sourceDirectoryPath = scanner.nextLine();
            }
        }
        System.out.println("Please enter target path: ");
        while (destinationDirectoryPath.isBlank()) {
            if (scanner.hasNextLine()) {
                destinationDirectoryPath = scanner.nextLine();
            }
        }
        System.out.println("Please enter thread amount: ");
        while (threadAmount == -1){
            if (scanner.hasNextInt()){
                int number = scanner.nextInt();
                scanner.nextLine();
                if (number > 0) threadAmount = number;
            } else scanner.nextLine();
        }
        scanner.close();

        CatalogAnalyzer analyzer = new CatalogAnalyzer(sourceDirectoryPath, destinationDirectoryPath, threadAmount);
        analyzer.analysis();
    }
}
