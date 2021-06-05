package pl.dele;

import java.util.Scanner;


/**
 * Klasa ConsoleVersionStart umożliwia uruchomienie aplikacji w wersji konsolowej (bez użycia GUI)
 * @author Daniel Delegacz
 */
public class ConsoleVersionStart {

    /**
     * Metoda main rozpoczynająca program
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // tworzę instancję skanera
        Scanner scanner = new Scanner(System.in);
        // ścieżka do katalogu w którym będą poszukiwane zdjęcia
        String sourceDirectoryPath = "";
        // ścieżka do katalogu do którego będą przenoszone zdjęcia
        String destinationDirectoryPath = "";
        // zmienna, która przechowuje informację o ilości wykorzystywanych wątków
        int threadAmount = -1;

        // pobierz od użytkownika ścieżkę do katalogu źródłowego
        System.out.println("Please enter the path to the directory to be sorted: ");
        while (sourceDirectoryPath.isBlank()) {
            if (scanner.hasNextLine()) {
                sourceDirectoryPath = scanner.nextLine();
            }
        }

        // pobierz od użytkownika ścieżkę do katalogu docelowego
        System.out.println("Please enter target path: ");
        while (destinationDirectoryPath.isBlank()) {
            if (scanner.hasNextLine()) {
                destinationDirectoryPath = scanner.nextLine();
            }
        }

        // pobierz od użytkownika informację o ilości wątków
        System.out.println("Please enter thread amount: ");
        while (threadAmount == -1){
            if (scanner.hasNextInt()){
                int number = scanner.nextInt();
                scanner.nextLine();
                if (number > 0) threadAmount = number;
            } else scanner.nextLine();
        }
        scanner.close();

        // utwórz obiekt CatalogAnalyzer o podanych parametrach i uruchom na nim metodę analysis()
        new CatalogAnalyzer(sourceDirectoryPath, destinationDirectoryPath, threadAmount).analysis();
    }
}
