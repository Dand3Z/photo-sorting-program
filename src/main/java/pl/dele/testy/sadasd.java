package pl.dele.testy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class sadasd {

    public static final String FOLDER_PATH = "photos";
    public static final String SEPARATOR = File.separator;

    private static Map<String, Integer> datesMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        File folder = new File(FOLDER_PATH);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".jpg"));
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .withLocale( Locale.getDefault() )
                .withZone(ZoneId.systemDefault() );


        for (File f: listOfFiles){
            Path sourcePath = f.toPath();
            BasicFileAttributes attributes = Files.readAttributes(sourcePath, BasicFileAttributes.class);
            FileTime time = attributes.creationTime();
            String date = formatter.format( time.toInstant() );

            // creating directories
            File fileDestination = new File(FOLDER_PATH + SEPARATOR + date);
            if (!datesMap.containsKey(date)) {
                datesMap.put(date, 1);
                fileDestination.mkdir();
            } else {
                Integer currentValue = datesMap.get(date);
                datesMap.put(date, currentValue + 1);
            }

            Path copiedPath = Paths.get(FOLDER_PATH + SEPARATOR + date + SEPARATOR + datesMap.get(date)
            + ".jpg");
            Files.copy(sourcePath, copiedPath, StandardCopyOption.REPLACE_EXISTING);

        }


//        Path path = Paths.get("resources/lorem-ipsum.txt");
//        BasicFileAttributes attr;
//        try {
//            attr = Files.readAttributes(path, BasicFileAttributes.class);
//            String outFormat = "%-20s: %s%n";
//            System.out.printf(outFormat, "creationTime", attr.creationTime());
//
//            System.out.printf("%n### bulk access to file attributes%n");
//            Map<String, Object> attrBulk;
//
//        } catch (IOException ex) {
//            System.err.println("failed to obtain BasicFileAttributes " + ex.getMessage());
//        }

    }
}
