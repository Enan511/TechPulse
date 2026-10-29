package ca.uwindsor.techpulse.regex;

/**
 * Finding patterns with regular expressions.
 * OWNER: Tengi Sherpa
 *
 * Feature: Finding patterns using regex (proposal 4.10).
 * Data structure / technique: compiled Pattern + Matcher with capturing groups.
 *
 * PRIORITY: every crawler calls parsePrice() and extractModelCode(), and the deal
 * comparator groups products by model code, so push these early.
 */
public class PatternExtractor {

    private PatternExtractor() {
        // only static helpers here
    }

    /**
     * Reads a Canadian dollar amount from messy text.
     * "$1,299.99 CAD" -> 1299.99, "Now only $489.99!" -> 489.99
     *
     * @return the first price found, or -1 if there is no price in the text
     */
    public static double parsePrice(String text) {
        // TODO (Tengi): pattern like \$?([0-9]{1,3}(?:,[0-9]{3})*(?:\.[0-9]{2})?)(?:\s*CAD)?
        //  strip the commas, Double.parseDouble the group
        return -1;
    }

    /**
     * Finds a manufacturer model / part code and normalises it (uppercase, spaces -> hyphens).
     * "MSI GeForce RTX 4070 Ventus 2X" -> "RTX-4070", "AMD Ryzen 7 7800X3D" -> "RYZEN-7-7800X3D"
     *
     * @return the normalised code, or "" if none is found
     */
    public static String extractModelCode(String text) {
        // TODO (Tengi): patterns for GPU (RTX/GTX/RX ####), CPU (Ryzen #/Core i#/Ultra #), RAM, SSD codes
        return "";
    }

    /**
     * Reads a discount like "Save 15%" or "-20% off".
     *
     * @return the percentage as a whole number, or -1 if there is none
     */
    public static int extractDiscountPercent(String text) {
        // TODO (Tengi)
        return -1;
    }
}
