package pl.dele;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Klasa sprawdzajaca poprawność kopiowania całego katalogu
 * przez CatalogAnalyzer
 * @author Daniel Delegacz
 */
public class CatalogAnalyzerTest {

    /**
     * Test sprawdzający poprawność kopiowania wyłączanie plików jpg
     * @throws InterruptedException
     */
    @Test
    public void shouldCopyOnlyJpgFiles() throws InterruptedException {
        File srcDirectoryFile = new File("photos");
        File dstDirectoryFile = new File("photos/dst");
        CatalogAnalyzer catalogAnalyzer = new CatalogAnalyzer(srcDirectoryFile.getAbsolutePath(),
                dstDirectoryFile.getAbsolutePath(),4);
        catalogAnalyzer.analysis();
        while (!catalogAnalyzer.isExecutorTerminated());

        File dateDirectoryFile = new File("photos/dst/2021-06-05");
        assertTrue(dateDirectoryFile.isDirectory());

        File copiedFile1 = new File("photos/dst/2021-06-05/1.jpg");
        assertTrue(copiedFile1.canRead());
        assertEquals("1.jpg", copiedFile1.getName());

        File copiedFile2 = new File("photos/dst/2021-06-05/2.jpg");
        assertTrue(copiedFile2.canRead());
        assertEquals("2.jpg", copiedFile2.getName());
    }
}
