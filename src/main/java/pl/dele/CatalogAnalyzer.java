package pl.dele;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CatalogAnalyzer {

    public static final String SEPARATOR = File.separator;
    private static final String FILE_EXTENSION = ".jpg";

    private final String CATALOG_PATH;
    private Map<String, Integer> datesMap; //

    private static DateTimeFormatter formatter = DateTimeFormatter
                                .ofPattern("yyyy-MM-dd")
                                .withLocale( Locale.getDefault() )
                                .withZone(ZoneId.systemDefault() );

    public CatalogAnalyzer(String catalogPath) {
        this.CATALOG_PATH = catalogPath;
        this.datesMap = new HashMap<>();
    }

    public void analysis() throws InterruptedException {
        File folder = new File(CATALOG_PATH);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));

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
//                map.put(date, new SingleFileCopy(CATALOG_PATH,CATALOG_PATH+SEPARATOR+date));
//                map.get(date).setFileName(f.getName());
//                map.get(date).start();
            } else {
                Integer currentValue = datesMap.get(date);
                datesMap.put(date, currentValue + 1);
//                map.get(date).setFileName(f.getName());
//                map.get(date).join();
//                map.get(date).start();
            }

            String copiedPath = CATALOG_PATH + SEPARATOR + date;
//
            new SingleFileCopy(f.getPath(), copiedPath,datesMap.get(date)).start();

//            Path copiedPath = Paths.get(CATALOG_PATH + SEPARATOR + date + SEPARATOR + datesMap.get(date)
//                    + ".jpg");
//
//            try {
//                Files.copy(sourcePath, copiedPath, StandardCopyOption.REPLACE_EXISTING);
//            }
//            catch (IOException e) { e.getMessage(); }
        }
    }

}
