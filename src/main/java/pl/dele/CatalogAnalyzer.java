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

/**
 * Klasa CatalogAnalyzer, która poszukuje w zadanym katalogu zdjęć JPG,
 * każde z nich analizuje pod kątem daty ich wykonania,
 * grupuje według daty wykonania, przenosi do katalogu nazwanego datą wykonania
 * numerując kolejno wewnątrz katalogów kolejnymi liczbami całowitymi
 * poczynając od 1.
 * @author Daniel Delegacz
 */
public class CatalogAnalyzer {

    // uniwersalny separator (/ dla Unixa, \ dla Windows)
    private static final String SEPARATOR = File.separator;
    // stała FILE_EXTENSION przechowująca rozszerzenie poszukiwanych plików
    private static final String FILE_EXTENSION = ".jpg";

    // stała zawierająca ścieżkę do katalogu źródłowego
    private final String SRC_CATALOG_PATH;
    // stała zawierająca ścieżkę do katalogu docelowego
    private final String DST_CATALOG_PATH;
    // stała przechowująca ilość wykorzystywanych wątków
    private final int THREAD_AMOUNT;
    // mapa przechowywująca informację o ostatnim wykorzystanym numerze danej daty
    private Map<String, Integer> datesCountMap;

    // obiekt typu ExecutorService
    private ExecutorService executor;

    // obiekt typu DateTimeFormatter przechowujący pożądany przez nas format daty
    private static DateTimeFormatter formatter = DateTimeFormatter
                                .ofPattern("yyyy-MM-dd")
                                .withLocale( Locale.getDefault() )
                                .withZone(ZoneId.systemDefault() );

    /**
     * Konstruktor klasy CatalogAnalyzer przyjmujący ścieżkę do katalogu źródłowego,
     * docelowego i liczbę wątków. Ustawia składowe i tworzy instancę mapy datesCountMap.
     * @param srcCatalogPath
     * @param dstCatalogPath
     * @param threadAmount
     */
    public CatalogAnalyzer(String srcCatalogPath, String dstCatalogPath, int threadAmount) {
        if (srcCatalogPath.isBlank() || dstCatalogPath.isBlank() || threadAmount < 0)
            throw new IllegalArgumentException();
        this.SRC_CATALOG_PATH = srcCatalogPath;
        this.DST_CATALOG_PATH = dstCatalogPath;
        this.THREAD_AMOUNT = threadAmount;
        this.datesCountMap = new HashMap<>();
    }

    /**
     * Metoda analizująca katalog źródłowy
     * @throws InterruptedException
     */
    public void analysis() throws InterruptedException {
        // inicjalizujemy executor o stałej liczbie wątków
        executor = Executors.newFixedThreadPool(THREAD_AMOUNT);

        // inicjalizuję obiekt typu File ścieżką katalogu źródłowego
        File folder = new File(SRC_CATALOG_PATH);

        /**
         * Analizuję katalog źródłowy pod względem rozszerzenia.
         * Tworzę tablicę obiektów typu File tylko z plikami z rozszerzeniem .jpg
         */
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));

        // pętla foreach po liście plików z rozszerzeniem .jpg
        for (File f: listOfFiles){
            Path sourcePath = f.toPath();
            String date = "";

            try {
                BasicFileAttributes attributes = Files.readAttributes(sourcePath, BasicFileAttributes.class);
                FileTime time = attributes.creationTime();
                date = formatter.format( time.toInstant() );
            } catch (IOException e) { e.getMessage(); }

            /**
             * tworzę obiekt typu File ze ścieżką do katalogu,
             * gdzie ma trafić aktualnie analizowany plik
             */
            File fileDestination = new File(DST_CATALOG_PATH + SEPARATOR + date);
            /**
             * sprawdzam czy taki folder był już wykorzystywany.
             * Jeśli był to znajduje się już taki klucz w mapie
              */
            if (!datesCountMap.containsKey(date)) {
                datesCountMap.put(date, 1);
                fileDestination.mkdir();
            } else {
                Integer currentValue = datesCountMap.get(date);
                datesCountMap.put(date, currentValue + 1);
            }

            /**
             * pełna ścieżka do katalogu docelowego dla aktualnie analizowanego pliku
             * (ścieżka docelowa / data)
             */
            String copiedPath = DST_CATALOG_PATH + SEPARATOR + date;
            /**
             * wywołanie metody execute obiektu executor podając jako argument
             * nowy obiekt typu SingleFileCopy z wymaganymi argumentami
             */
            executor.execute(new SingleFileCopy(f.getPath(), copiedPath, datesCountMap.get(date)));
        }
        executor.shutdown();
    }

    /**
     * publicza metoda pozwalająca sprawdzić czy executor już zakończył pracę
     * @return czy executor zakończył pracę
     */
    public boolean isExecutorTerminated(){
        return executor.isTerminated();
    }

}
