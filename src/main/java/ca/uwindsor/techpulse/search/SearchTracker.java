package ca.uwindsor.techpulse.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Remembers what users searched for and how many times.
 * OWNER: Sheikh MD. Nafis Noor Nabil
 *
 * Feature: Search frequency (proposal 4.6).
 * Data structure: HashMap<String, Integer> + PriorityQueue max-heap for the top-K.
 */
public class SearchTracker {

    /** Call once every time the user searches (the app passes the cleaned, lowercase query). */
    public void record(String query) {
        // TODO (Nabil)
    }

    /** @return how many times this query was searched, 0 if never */
    public int timesSearched(String query) {
        // TODO (Nabil)
        return 0;
    }

    /** @return the k most searched queries with their counts, most searched first */
    public List<Map.Entry<String, Integer>> topSearches(int k) {
        // TODO (Nabil): load the entries into a max-heap and poll k times
        return new ArrayList<>();
    }
}
