package ca.uwindsor.techpulse.crawler;

import java.util.ArrayList;
import java.util.List;

import ca.uwindsor.techpulse.model.Product;

/**
 * Crawler for ithardwarehub.ca (https://www.ithardwarehub.ca)
 * OWNER: Tengi Sherpa
 *
 * Feature: Web crawler (BFS). See docs/TEAM_TASKS.md for the full checklist.
 */
public class ITHubCrawler implements StoreCrawler {

    // TODO (Tengi Sherpa): put the real category page links (GPU, CPU, RAM, SSD) here
    private static final String[] SEED_URLS = {
            "https://www.ithardwarehub.ca/"
    };

    @Override
    public String storeName() {
        return "ithardwarehub";
    }

    @Override
    public List<Product> crawl(int maxPages) {
        List<Product> products = new ArrayList<>();

        // TODO (Tengi Sherpa): BFS crawl as described in the proposal (section 4.1)
        //  - queue of URLs to visit (LinkedList used as a FIFO queue), start with SEED_URLS
        //  - HashSet<String> of visited URLs so no page is fetched twice
        //  - only follow links on ithardwarehub.ca, and stop after maxPages pages
        //  - wait 500 ms between requests (Thread.sleep) to be polite to the store
        //  - fetch with Jsoup (or Selenium if the site blocks Jsoup), save the raw HTML to
        //    data/raw/ithardwarehub_<n>.html so you can debug your selectors offline
        //  - for each product card: build a Product (use PatternExtractor.parsePrice and
        //    PatternExtractor.extractModelCode from Tengi's file for price and model code)
        //  - catch IOException / HTTP 403 / 404 / timeouts per page, print a warning, keep going

        return products;
    }
}
