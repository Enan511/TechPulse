package ca.uwindsor.techpulse.index;

import java.util.ArrayList;
import java.util.List;

/**
 * One entry in a word's posting list: which product page has the word, how often, and where.
 * OWNER: Mohammad Enan Al Harun Sahan (part of the inverted index feature)
 */
public class Posting {

    private final int docId;                                  // position of the product in the loaded product list
    private int frequency;                                    // how many times the word appears on that page
    private final List<Integer> positions = new ArrayList<>(); // token positions, for phrase queries

    public Posting(int docId) {
        this.docId = docId;
    }

    /** Called once for each time the word is seen on this page. */
    public void addOccurrence(int position) {
        frequency++;
        positions.add(position);
    }

    public int getDocId() { return docId; }
    public int getFrequency() { return frequency; }
    public List<Integer> getPositions() { return positions; }
}
