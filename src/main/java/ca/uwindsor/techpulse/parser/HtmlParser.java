package ca.uwindsor.techpulse.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * HTML parsing and cleaning.
 * OWNER: Tengi Sherpa
 *
 * Feature: HTML parser (proposal 4.2). Data structure: Jsoup DOM tree + ArrayList of tokens.
 *
 * PRIORITY: tokenize() is used by the inverted index, frequency counter, trie and spell
 * checker, so please finish it first and push it early.
 */
public class HtmlParser {

    private HtmlParser() {
        // only static helpers here
    }

    /**
     * Turns raw HTML into plain readable text.
     *
     * @param html a whole page or one product card
     * @return the visible text without tags, scripts, styles, header/nav/footer; "" for null/empty input
     */
    public static String clean(String html) {
        // TODO (Tengi): Jsoup.parse(html), remove script/style/nav/header/footer, return doc.text()
        return "";
    }

    /**
     * Splits text into lowercase search words.
     * Model codes must survive as one token: "RTX-4070" -> "rtx-4070", "Ryzen-7-7800X3D" -> "ryzen-7-7800x3d".
     *
     * @param text plain text (e.g. Product.getSearchText())
     * @return the tokens in order, never null (empty list for null/empty input)
     */
    public static List<String> tokenize(String text) {
        // TODO (Tengi): lowercase, split on spaces/punctuation but keep hyphens inside codes,
        //  drop empty tokens (and maybe 1-letter tokens)
        return new ArrayList<>();
    }
}
