package ca.uwindsor.techpulse.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ca.uwindsor.techpulse.model.Product;

/**
 * Saves and loads products as CSV files (SHARED FILE - owner: Nikhil).
 *
 * Crawlers write data/products/<store>.csv and the app reads every CSV in that folder,
 * so the features can be built and tested without crawling the live sites every time.
 */
public class ProductCsv {

    public static final String HEADER = "store,title,category,modelNumber,price,inStock,url,description";
    private static final int COLUMN_COUNT = 8;

    private ProductCsv() {
        // only static helpers here
    }

    /** Writes the products to the file, replacing it if it exists. */
    public static void write(Path file, List<Product> products) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        try (BufferedWriter out = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            out.write(HEADER);
            out.newLine();
            for (Product p : products) {
                out.write(String.join(",",
                        quote(p.getStore()), quote(p.getTitle()), quote(p.getCategory()),
                        quote(p.getModelNumber()), String.valueOf(p.getPrice()),
                        String.valueOf(p.isInStock()), quote(p.getUrl()), quote(p.getDescription())));
                out.newLine();
            }
        }
    }

    /**
     * Reads one CSV file. Bad rows are skipped with a warning instead of stopping
     * the whole load, because one broken line from a crawler shouldn't kill the app.
     */
    public static List<Product> read(Path file) throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader in = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line = in.readLine(); // header row
            int lineNumber = 1;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                List<String> cells = splitRow(line);
                if (cells.size() != COLUMN_COUNT) {
                    System.out.println("  [warn] " + file.getFileName() + " line " + lineNumber
                            + ": expected " + COLUMN_COUNT + " columns but found " + cells.size() + ", skipped");
                    continue;
                }
                double price;
                try {
                    price = Double.parseDouble(cells.get(4));
                } catch (NumberFormatException e) {
                    price = -1; // keep the product, just without a usable price
                }
                products.add(new Product(cells.get(0), cells.get(1), cells.get(2), cells.get(3),
                        price, Boolean.parseBoolean(cells.get(5)), cells.get(6), cells.get(7)));
            }
        }
        return products;
    }

    /** Reads every .csv file in a folder (one per store) into one list. */
    public static List<Product> readFolder(Path folder) throws IOException {
        List<Product> all = new ArrayList<>();
        if (!Files.isDirectory(folder)) {
            return all;
        }
        try (DirectoryStream<Path> files = Files.newDirectoryStream(folder, "*.csv")) {
            for (Path file : files) {
                List<Product> fromFile = read(file);
                System.out.println("  loaded " + fromFile.size() + " products from " + file.getFileName());
                all.addAll(fromFile);
            }
        }
        return all;
    }

    // wrap in quotes and double any quote inside, so commas in titles don't split the row
    private static String quote(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    // splits one CSV row, respecting quoted cells like "RTX 4070, 12GB"
    static List<String> splitRow(String line) {
        List<String> cells = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (insideQuotes) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"'); // "" inside quotes means a real quote
                    i++;
                } else if (c == '"') {
                    insideQuotes = false;
                } else {
                    current.append(c);
                }
            } else if (c == '"') {
                insideQuotes = true;
            } else if (c == ',') {
                cells.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        cells.add(current.toString());
        return cells;
    }
}
