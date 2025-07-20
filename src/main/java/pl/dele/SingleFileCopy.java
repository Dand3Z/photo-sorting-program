package pl.dele;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SingleFileCopy extends Thread {

    private static final String FILE_EXTENSION = ".jpg";

    private final String SOURCE_FILE_PATH;
    private final String DESTINATION_DIRECTORY_PATH;
    private final int number;

    public SingleFileCopy(String SOURCE_FILE_PATH, String DESTINATION_DIRECTORY_PATH, int number){
        this.SOURCE_FILE_PATH = SOURCE_FILE_PATH;
        this.DESTINATION_DIRECTORY_PATH = DESTINATION_DIRECTORY_PATH;
        this.number = number;
    }

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
