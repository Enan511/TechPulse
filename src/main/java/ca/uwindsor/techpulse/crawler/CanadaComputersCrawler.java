package ca.uwindsor.techpulse.crawler;

import java.util.ArrayList;
import java.util.List;

import ca.uwindsor.techpulse.model.Product;

/**
 * Crawler for canadacomputers.com (https://www.canadacomputers.com)
 * OWNER: Mohammad Enan Al Harun Sahan
 *
 * Feature: Web crawler (BFS). See docs/TEAM_TASKS.md for the full checklist.
 */
public class CanadaComputersCrawler implements StoreCrawler {

    // TODO (Mohammad Enan Al Harun Sahan): put the real category page links (GPU, CPU, RAM, SSD) here
    private static final String[] SEED_URLS = {
            "https://www.canadacomputers.com/"
    };

    @Override
    public String storeName() {
        return "canada-computers";
    }

    @Override
    public List<Product> crawl(int maxPages) {
        List<Product> products = new ArrayList<>();

        // TODO (Mohammad Enan Al Harun Sahan): BFS crawl as described in the proposal (section 4.1)
        //  - queue of URLs to visit (LinkedList used as a FIFO queue), start with SEED_URLS
        //  - HashSet<String> of visited URLs so no page is fetched twice
        //  - only follow links on canadacomputers.com, and stop after maxPages pages
        //  - wait 500 ms between requests (Thread.sleep) to be polite to the store
        //  - fetch with Jsoup (or Selenium if the site blocks Jsoup), save the raw HTML to
        //    data/raw/canada-computers_<n>.html so you can debug your selectors offline
        //  - for each product card: build a Product (use PatternExtractor.parsePrice and
        //    PatternExtractor.extractModelCode from Tengi's file for price and model code)
        //  - catch IOException / HTTP 403 / 404 / timeouts per page, print a warning, keep going

        return products;
    }
}
