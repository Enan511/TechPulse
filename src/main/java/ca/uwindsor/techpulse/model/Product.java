package ca.uwindsor.techpulse.model;

/**
 * One product listing scraped from one store (SHARED FILE - ask Nikhil before changing it).
 *
 * Every crawler turns the store's product cards into Product objects, and every
 * feature (index, ranking, spell check, deals...) reads them. Keeping one common
 * shape is what lets the five crawlers and the features plug together.
 *
 * Saved in CSV with the header:
 * store,title,category,modelNumber,price,inStock,url,description
 */
public class Product {

    private final String store;        // e.g. "pc-canada" (same as the crawler's storeName())
    private final String title;        // full product name as shown on the site
    private final String category;     // GPU, CPU, RAM or SSD
    private final String modelNumber;  // manufacturer part / model code, "" if not found
    private final double price;        // price in CAD, -1 if the price could not be read
    private final boolean inStock;
    private final String url;          // product page link, also used as the page "id"
    private final String description;  // extra text from the product page, "" if none

    public Product(String store, String title, String category, String modelNumber,
                   double price, boolean inStock, String url, String description) {
        // nulls would break the CSV writer and the tokenizer later, so store "" instead
        this.store = safe(store);
        this.title = safe(title);
        this.category = safe(category);
        this.modelNumber = safe(modelNumber);
        this.price = price;
        this.inStock = inStock;
        this.url = safe(url);
        this.description = safe(description);
    }

    private static String safe(String value) {
        return value == null ? "" : value.trim();
    }

    public String getStore() { return store; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getModelNumber() { return modelNumber; }
    public double getPrice() { return price; }
    public boolean isInStock() { return inStock; }
    public String getUrl() { return url; }
    public String getDescription() { return description; }

    /** True when the crawler managed to read a price for this listing. */
    public boolean hasPrice() {
        return price >= 0;
    }

    /** All the text of this listing joined together; this is what the index and frequency counter read. */
    public String getSearchText() {
        return title + " " + category + " " + modelNumber + " " + description;
    }

    @Override
    public String toString() {
        String priceText = hasPrice() ? String.format("$%.2f", price) : "price n/a";
        String stock = inStock ? "in stock" : "out of stock";
        return String.format("[%s] %s | %s | %s | %s", store, title, priceText, stock, url);
    }
}
