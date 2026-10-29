package ca.uwindsor.techpulse.regex;

/**
 * Checks every user input before it reaches the search features.
 * OWNER: Nikhil Mahara
 *
 * Feature: Data validation using regular expressions (proposal 4.9).
 * Data structure / technique: compiled Pattern + Matcher.
 */
public class InputValidator {

    private InputValidator() {
        // only static helpers here
    }

    /**
     * A search keyword: letters, digits, spaces and hyphens, 2 to 60 characters.
     * "rtx 4070" ok, "ryzen-7" ok, "a" too short, "<script>" bad characters.
     */
    public static boolean isValidKeyword(String input) {
        // TODO (Nikhil): Pattern like ^[A-Za-z0-9][A-Za-z0-9 \-]{1,59}$ ; null -> false
        return input != null && !input.isBlank(); // placeholder so the menu works until the regex is in
    }

    /**
     * A price filter typed by the user: "500", "499.99", "$1,299.99".
     */
    public static boolean isValidPrice(String input) {
        // TODO (Nikhil): ^\$?\d{1,3}(,\d{3})*(\.\d{1,2})?$ or ^\d+(\.\d{1,2})?$
        return false;
    }

    /**
     * A product page link typed by the user (used by the frequency-count menu option).
     */
    public static boolean isValidUrl(String input) {
        // TODO (Nikhil): ^https?://[\w.-]+(/\S*)?$
        return input != null && !input.isBlank();
    }
}
