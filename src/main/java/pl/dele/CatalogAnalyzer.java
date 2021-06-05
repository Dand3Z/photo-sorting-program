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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CatalogAnalyzer {

    private static final String SEPARATOR = File.separator;
    private static final String FILE_EXTENSION = ".jpg";

    private final String SRC_CATALOG_PATH;
    private final String DST_CATALOG_PATH;
    private final int THREAD_AMOUNT;
    private Map<String, Integer> datesCountMap;

    private ExecutorService executor;

    private static DateTimeFormatter formatter = DateTimeFormatter
                                .ofPattern("yyyy-MM-dd")
                                .withLocale( Locale.getDefault() )
                                .withZone(ZoneId.systemDefault() );

    public CatalogAnalyzer(String srcCatalogPath, String dstCatalogPath, int threadAmount) {
        this.SRC_CATALOG_PATH = srcCatalogPath;
        this.DST_CATALOG_PATH = dstCatalogPath;
        this.THREAD_AMOUNT = threadAmount;
        this.datesCountMap = new HashMap<>();
    }

    public void analysis() throws InterruptedException {
        executor = Executors.newFixedThreadPool(THREAD_AMOUNT);

        File folder = new File(SRC_CATALOG_PATH);
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
            File fileDestination = new File(DST_CATALOG_PATH + SEPARATOR + date);
            if (!datesCountMap.containsKey(date)) {
                datesCountMap.put(date, 1);
                fileDestination.mkdir();
            } else {
                Integer currentValue = datesCountMap.get(date);
                datesCountMap.put(date, currentValue + 1);
            }

            String copiedPath = DST_CATALOG_PATH + SEPARATOR + date;
            executor.execute(new SingleFileCopy(f.getPath(), copiedPath, datesCountMap.get(date)));
        }
        executor.shutdown();
    }

    public boolean isExecutorShutdown(){
        return executor.isShutdown();
    }

}
