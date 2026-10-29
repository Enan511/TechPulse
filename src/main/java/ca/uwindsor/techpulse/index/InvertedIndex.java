package ca.uwindsor.techpulse.index;

import java.util.ArrayList;
import java.util.List;

import ca.uwindsor.techpulse.model.Product;

/**
 * Word -> list of pages that contain it, so a search doesn't scan every product.
 * OWNER: Mohammad Enan Al Harun Sahan
 *
 * Feature: Inverted indexing (proposal 4.8).
 * Data structure: HashMap<String, List<Posting>>.
 */
public class InvertedIndex {

    // TODO (Mohammad): private final Map<String, List<Posting>> index = new HashMap<>();

    /**
     * Builds the index. docId of a product = its position in the list.
     * Use HtmlParser.tokenize(product.getSearchText()) to get the words.
     */
    public void build(List<Product> products) {
        // TODO (Mohammad)
    }

    /**
     * @param word one search word (any case)
     * @return the postings for the word, or an empty list if the word is not indexed
     */
    public List<Posting> lookup(String word) {
        // TODO (Mohammad)
        return new ArrayList<>();
    }

    /** @return true if at least one page contains the word */
    public boolean contains(String word) {
        return !lookup(word).isEmpty();
    }

    /** @return how many different words are in the index */
    public int size() {
        // TODO (Mohammad)
        return 0;
    }
}
