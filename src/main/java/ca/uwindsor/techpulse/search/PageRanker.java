package ca.uwindsor.techpulse.search;

import java.util.ArrayList;
import java.util.List;

import ca.uwindsor.techpulse.index.InvertedIndex;
import ca.uwindsor.techpulse.model.Product;

/**
 * Orders search results so pages that mention the keywords more often come first.
 * OWNER: Mohammad Enan Al Harun Sahan
 *
 * Feature: Page ranking (proposal 4.7).
 * Data structure: score per page + PriorityQueue max-heap with a custom Comparator.
 */
public class PageRanker {

    private final InvertedIndex index;
    private final List<Product> products;

    public PageRanker(InvertedIndex index, List<Product> products) {
        this.index = index;
        this.products = products;
    }

    /**
     * @param queryWords the search words (already lowercase / tokenized)
     * @param k how many results to return
     * @return up to k products, best match first; empty if no page has any of the words
     */
    public List<Product> topPages(List<String> queryWords, int k) {
        // TODO (Mohammad): for each word, index.lookup(word) -> add posting frequency to that page's score,
        //  put (product, score) pairs in a max-heap, poll k times, return products.get(docId) in order
        return new ArrayList<>();
    }
}
