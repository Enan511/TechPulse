package ca.uwindsor.techpulse.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Autocomplete: type "ryz" and get "ryzen", "ryzen-7-7800x3d", ...
 * OWNER: Aditya Mahmud
 *
 * Feature: Word completion (proposal 4.4).
 * Data structure: Trie with HashMap<Character, TrieNode> children + DFS to collect words.
 */
public class WordCompletionTrie {

    // TODO (Aditya): private TrieNode root (inner class with children map, isWord flag, maybe a count)

    /** Adds one word (the app inserts every token from the products). */
    public void insert(String word) {
        // TODO (Aditya)
    }

    /**
     * @param prefix what the user has typed so far
     * @param k max number of suggestions
     * @return up to k words starting with the prefix (most frequent first if you track counts), empty if none
     */
    public List<String> complete(String prefix, int k) {
        // TODO (Aditya): walk down the prefix, then DFS the subtree
        return new ArrayList<>();
    }
}
