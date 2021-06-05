package pl.dele;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Klasa odpowiedzialna za skopiowanie jednego pliku do katalogu docelowego.
 * Rozszerza klasę Thread.
 * @author Daniel Delegacz
 */
public class SingleFileCopy extends Thread {

    // stała przechowująca rozszerzenie pliku
    private static final String FILE_EXTENSION = ".jpg";

    // ścieżka źródłowa do pliku, który ma zostać skopiowany
    private final String SOURCE_FILE_PATH;
    // ściżka źródłowa do katalogu do którego ma zostać skopiowany plik
    private final String DESTINATION_DIRECTORY_PATH;
    // numer pliku w katalogu
    private final int number;

    /**
     * Konstruktor ustawiający ścieżkę do pliku źródłowego, katalogu docelowego i numer pliku
     * @param SOURCE_FILE_PATH
     * @param DESTINATION_DIRECTORY_PATH
     * @param number
     */
    public SingleFileCopy(String SOURCE_FILE_PATH, String DESTINATION_DIRECTORY_PATH, int number){
        if (SOURCE_FILE_PATH == null || DESTINATION_DIRECTORY_PATH == null || number < 0)
            throw new IllegalArgumentException();
        this.SOURCE_FILE_PATH = SOURCE_FILE_PATH;
        this.DESTINATION_DIRECTORY_PATH = DESTINATION_DIRECTORY_PATH;
        this.number = number;
    }

    /**
     * Nadpisana metoda run z klasy Thread.
     * Metoda odpowiada za skopiowanie pliku z katalogu źródłowego do katalogu docelowego.
     */
    @Override
    public void run() {
        Path sourcePath = Paths.get(SOURCE_FILE_PATH);
        Path copiedPath = Paths.get(DESTINATION_DIRECTORY_PATH + File.separator + number + FILE_EXTENSION);

        try {
            Files.copy(sourcePath, copiedPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) { e.getMessage(); }
    }
}
