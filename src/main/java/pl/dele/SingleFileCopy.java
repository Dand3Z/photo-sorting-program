package pl.dele;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SingleFileCopy extends Thread {

    private final String SOURCE_DIRECTORY_PATH;
    private final String DESTINATION_DIRECTORY_PATH;

    private int number;
    private String fileName;

    public SingleFileCopy(String SOURCE_DIRECTORY_PATH, String DESTINATION_DIRECTORY_PATH){
        this.SOURCE_DIRECTORY_PATH = SOURCE_DIRECTORY_PATH;
        this.DESTINATION_DIRECTORY_PATH = DESTINATION_DIRECTORY_PATH;
        this.number = 1;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {

        Path sourcePath = Paths.get(SOURCE_DIRECTORY_PATH + File.separator + fileName);
        Path copiedPath = Paths.get(DESTINATION_DIRECTORY_PATH + File.separator + number);

        try {
            Files.copy(sourcePath, copiedPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) { e.getMessage(); }


    }
}
