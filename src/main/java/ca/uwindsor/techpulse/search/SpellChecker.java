package ca.uwindsor.techpulse.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Suggests the right word when the user misspells a search term.
 * OWNER: Sheikh MD. Nafis Noor Nabil
 *
 * Feature: Spell checking (proposal 4.3).
 * Data structure / algorithm: vocabulary set (hash table) + Levenshtein edit distance with a 2D matrix.
 */
public class SpellChecker {

    /**
     * @param vocabulary every word from the crawled products (the app passes all tokens)
     */
    public SpellChecker(Collection<String> vocabulary) {
        // TODO (Nabil): store the vocabulary (HashSet, or your cuckoo hash table from Assignment 2)
    }

    /** @return true if the word exists in the vocabulary (ignore case) */
    public boolean isKnown(String word) {
        // TODO (Nabil)
        return false;
    }

    /**
     * @param word the misspelled word
     * @param maxEdits largest edit distance allowed (the proposal uses 2)
     * @return close vocabulary words, closest first (empty list if none)
     */
    public List<String> suggest(String word, int maxEdits) {
        // TODO (Nabil): editDistance(word, candidate) for each vocabulary word, keep <= maxEdits, sort by distance
        return new ArrayList<>();
    }

    /** Levenshtein distance between two words using an (m+1) x (n+1) table. */
    public static int editDistance(String a, String b) {
        // TODO (Nabil)
        return Integer.MAX_VALUE;
    }
}
