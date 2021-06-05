package pl.dele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Klasa sprawdzająca poprawność kopiowania pojedyńczego pliku przez
 * SingleFileCopy
 */
public class SingleFileCopyTest {

    // ścieżka do katalogu źródłowego
    private File dstCatalogFile;

    @BeforeEach
    public void init(){
        dstCatalogFile = new File("photos/dst/2020-10-12");
        dstCatalogFile.mkdir();
    }

    @Test
    public void shouldCopySingleFile(){
        File srcFile = new File("photos/sample.jpg");
        File dstDictPath = new File("photos/dst/2020-10-12");
        SingleFileCopy sfc = new SingleFileCopy(srcFile.getAbsolutePath(), dstDictPath.getAbsolutePath(), 1);
        sfc.run(); // one thread version
        File copiedFile = new File("photos/dst/2020-10-12/1.jpg");
        assertTrue(copiedFile.canRead());
    }

}
