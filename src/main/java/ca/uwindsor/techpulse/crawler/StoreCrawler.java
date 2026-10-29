package ca.uwindsor.techpulse.crawler;

import java.util.List;

import ca.uwindsor.techpulse.model.Product;

/**
 * What every store crawler must provide (SHARED FILE - ask Nikhil before changing it).
 *
 * The main app only talks to crawlers through this interface, so each member can write
 * their crawler however they like inside their own class.
 */
public interface StoreCrawler {

    /**
     * Short name of the store, lowercase with dashes, e.g. "pc-canada".
     * It is also the CSV file name: data/products/pc-canada.csv
     */
    String storeName();

    /**
     * Crawl the store and return the products found.
     * Must never throw for a single bad page: catch it, print a warning, and move on.
     *
     * @param maxPages stop after visiting this many pages (keeps demo runs short and polite)
     * @return the products found, or an empty list if nothing could be read
     */
    List<Product> crawl(int maxPages);
}
