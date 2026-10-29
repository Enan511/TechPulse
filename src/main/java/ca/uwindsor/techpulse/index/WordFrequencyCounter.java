package ca.uwindsor.techpulse.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.uwindsor.techpulse.model.Product;

/**
 * How many times a word appears on a specific product page (URL).
 * OWNER: Aditya Mahmud
 *
 * Feature: Frequency count (proposal 4.5).
 * Data structure: one HashMap<String, Integer> per page (e.g. Map<url, Map<word, count>>).
 */
public class WordFrequencyCounter {

    /** Counts the words of every product. Use HtmlParser.tokenize(product.getSearchText()). */
    public void build(List<Product> products) {
        // TODO (Aditya)
    }

    /**
     * @return how many times the word appears on the page with this URL, 0 if the word or URL is unknown
     */
    public int countInPage(String url, String word) {
        // TODO (Aditya)
        return 0;
    }

    /**
     * @return the n most frequent words on the page, highest count first (empty if the URL is unknown)
     */
    public List<Map.Entry<String, Integer>> topWords(String url, int n) {
        // TODO (Aditya): sort the page's entries by count (your own sort from Assignment 2 is fine)
        return new ArrayList<>();
    }
}
