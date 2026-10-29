package ca.uwindsor.techpulse.deals;

import java.util.ArrayList;
import java.util.List;

import ca.uwindsor.techpulse.model.Product;

/**
 * Finds the same part across the five stores and shows who sells it cheapest.
 * OWNER: Nikhil Mahara
 *
 * Feature: Product matcher and deal comparator (proposal 4.11).
 * Data structure: HashMap<String, List<Product>> keyed by model code + PriorityQueue min-heap by price.
 */
public class DealComparator {

    /**
     * Groups the products by normalised model number. Products without a model number or
     * without a price (price < 0) are skipped.
     */
    public DealComparator(List<Product> products) {
        // TODO (Nikhil)
    }

    /**
     * @param modelCode e.g. "RTX-4070" (any case)
     * @return every listing of that part, cheapest first; empty if the part is unknown
     */
    public List<Product> offersFor(String modelCode) {
        // TODO (Nikhil): copy the group into a min-heap ordered by price and poll everything
        return new ArrayList<>();
    }

    /** @return the cheapest listing of the part, or null if it is unknown */
    public Product bestOffer(String modelCode) {
        List<Product> offers = offersFor(modelCode);
        return offers.isEmpty() ? null : offers.get(0);
    }
}
