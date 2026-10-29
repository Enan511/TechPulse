package ca.uwindsor.techpulse.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ca.uwindsor.techpulse.model.Product;

/** Tests for the shared CSV helper. Also a working example of how our tests are written. */
class ProductCsvTest {

    @TempDir
    Path tempFolder;

    @Test
    void savedProductsComeBackTheSame() throws IOException {
        Product gpu = new Product("pc-canada", "RTX 4070, 12GB \"OC\" edition", "GPU", "RTX-4070",
                729.99, true, "https://example.com/a", "");
        Path file = tempFolder.resolve("pc-canada.csv");

        ProductCsv.write(file, List.of(gpu));
        List<Product> loaded = ProductCsv.read(file);

        assertEquals(1, loaded.size());
        assertEquals("RTX 4070, 12GB \"OC\" edition", loaded.get(0).getTitle()); // comma and quotes survive
        assertEquals(729.99, loaded.get(0).getPrice(), 0.001);
        assertTrue(loaded.get(0).isInStock());
    }

    @Test
    void brokenRowsAreSkippedNotFatal() throws IOException {
        Path file = tempFolder.resolve("broken.csv");
        Files.writeString(file, ProductCsv.HEADER + "\n"
                + "\"vuugo\",\"only three\",\"cells\"\n"
                + "\"vuugo\",\"Good row\",\"CPU\",\"\",not-a-number,false,\"https://example.com/b\",\"\"\n");

        List<Product> loaded = ProductCsv.read(file);

        assertEquals(1, loaded.size());
        assertFalse(loaded.get(0).hasPrice()); // unreadable price becomes -1 instead of crashing
    }

    @Test
    void missingFolderGivesEmptyList() throws IOException {
        assertTrue(ProductCsv.readFolder(tempFolder.resolve("does-not-exist")).isEmpty());
    }
}
