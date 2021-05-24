package pl.dele.testy;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;

public class MyExampleWithoutApache {

    public static final String FOLDER_PATH = "photos";

    public static void main(String[] args) throws IOException {

        File folder = new File(FOLDER_PATH);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".jpg"));

        for (File f: listOfFiles){
            SimpleDateFormat s = new SimpleDateFormat("dd.MM.yyyy");
            Path path = f.toPath();
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime time = attributes.creationTime();
            //time.toInstant().


            //System.out.println(s.parse(time.toInstant()));
        }
    }
}
