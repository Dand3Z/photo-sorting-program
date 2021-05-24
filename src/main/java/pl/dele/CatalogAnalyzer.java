package pl.dele;

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

public class CatalogAnalyzer {

    public static final String SEPARATOR = File.separator;
    private final String CATALOG_PATH;
    private Map<String, Integer> datesMap;

    private static DateTimeFormatter formatter = DateTimeFormatter
                                .ofPattern("yyyy-MM-dd")
                                .withLocale( Locale.getDefault() )
                                .withZone(ZoneId.systemDefault() );

    public CatalogAnalyzer(String catalogPath) {
        this.CATALOG_PATH = catalogPath;
        this.datesMap = new HashMap<>();
    }

    public void analysis(){
        File folder = new File(CATALOG_PATH);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".jpg"));

        for (File f: listOfFiles){
            Path sourcePath = f.toPath();
            String date = "";

            try {
                BasicFileAttributes attributes = Files.readAttributes(sourcePath, BasicFileAttributes.class);
                FileTime time = attributes.creationTime();
                date = formatter.format( time.toInstant() );
            } catch (IOException e) { e.getMessage(); }

            // creating directories
            File fileDestination = new File(CATALOG_PATH + SEPARATOR + date);
            if (!datesMap.containsKey(date)) {
                datesMap.put(date, 1);
                fileDestination.mkdir();
            } else {
                Integer currentValue = datesMap.get(date);
                datesMap.put(date, currentValue + 1);
            }

            Path copiedPath = Paths.get(CATALOG_PATH + SEPARATOR + date + SEPARATOR + datesMap.get(date)
                    + ".jpg");

            try {
                Files.copy(sourcePath, copiedPath, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e) { e.getMessage(); }
        }
    }

}
