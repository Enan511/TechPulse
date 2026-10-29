package ca.uwindsor.techpulse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import ca.uwindsor.techpulse.crawler.CanadaComputersCrawler;
import ca.uwindsor.techpulse.crawler.ITHubCrawler;
import ca.uwindsor.techpulse.crawler.PCCanadaCrawler;
import ca.uwindsor.techpulse.crawler.PccZoneCrawler;
import ca.uwindsor.techpulse.crawler.StoreCrawler;
import ca.uwindsor.techpulse.crawler.VuugoCrawler;
import ca.uwindsor.techpulse.deals.DealComparator;
import ca.uwindsor.techpulse.index.InvertedIndex;
import ca.uwindsor.techpulse.index.WordFrequencyCounter;
import ca.uwindsor.techpulse.model.Product;
import ca.uwindsor.techpulse.parser.HtmlParser;
import ca.uwindsor.techpulse.regex.InputValidator;
import ca.uwindsor.techpulse.search.PageRanker;
import ca.uwindsor.techpulse.search.SearchTracker;
import ca.uwindsor.techpulse.search.SpellChecker;
import ca.uwindsor.techpulse.search.WordCompletionTrie;
import ca.uwindsor.techpulse.storage.ProductCsv;

/**
 * TechPulse console app: the place where every member's feature is plugged together.
 * OWNER: Nikhil Mahara (integration). Ask before changing, since every feature is called from here.
 *
 * Run it from VS Code: open this file and click "Run" above main, or use Run and Debug -> "Run TechPulse".
 */
public class TechPulseApp {

    private static final Path PRODUCTS_FOLDER = Path.of("data", "products");
    private static final Path SAMPLE_FOLDER = Path.of("data", "sample");
    private static final int RESULTS_TO_SHOW = 10;
    private static final int PAGES_PER_CRAWL = 20;

    private final StoreCrawler[] crawlers = {
            new CanadaComputersCrawler(),
            new PccZoneCrawler(),
            new VuugoCrawler(),
            new PCCanadaCrawler(),
            new ITHubCrawler()
    };

    private final SearchTracker searchTracker = new SearchTracker(); // kept across reloads (whole session)
    private final Scanner input = new Scanner(System.in);

    private List<Product> products = new ArrayList<>();
    private InvertedIndex index;
    private WordFrequencyCounter frequencyCounter;
    private WordCompletionTrie trie;
    private SpellChecker spellChecker;
    private PageRanker ranker;
    private DealComparator deals;

    public static void main(String[] args) {
        new TechPulseApp().run();
    }

    private void run() {
        System.out.println("=== TechPulse: Canadian PC hardware price search ===");
        loadProducts();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Choose an option: ");
            try {
                switch (choice) {
                    case "1" -> searchProducts();
                    case "2" -> completeWord();
                    case "3" -> showTopSearches();
                    case "4" -> showWordFrequency();
                    case "5" -> compareDeals();
                    case "6" -> runCrawlers();
                    case "0" -> running = false;
                    default -> System.out.println("Please type a number from the menu.");
                }
            } catch (RuntimeException e) {
                // one broken feature should not crash the whole demo
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
        System.out.println("Bye!");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Search products");
        System.out.println("2. Word completion");
        System.out.println("3. Most searched words");
        System.out.println("4. Word frequency on a product page");
        System.out.println("5. Cheapest store for a part (deal comparator)");
        System.out.println("6. Crawl the stores again");
        System.out.println("0. Exit");
    }

    // Loads the crawled CSVs (or the sample data if nobody has crawled yet) and rebuilds every structure.
    private void loadProducts() {
        try {
            products = ProductCsv.readFolder(PRODUCTS_FOLDER);
            if (products.isEmpty()) {
                System.out.println("No crawled data in data/products yet, using the sample data instead.");
                products = ProductCsv.readFolder(SAMPLE_FOLDER);
            }
        } catch (IOException e) {
            System.out.println("Could not read the product files: " + e.getMessage());
            products = new ArrayList<>();
        }
        System.out.println("Products loaded: " + products.size());
        buildStructures();
    }

    private void buildStructures() {
        index = new InvertedIndex();
        index.build(products);

        frequencyCounter = new WordFrequencyCounter();
        frequencyCounter.build(products);

        // the same words feed the trie and the spell checker's vocabulary
        trie = new WordCompletionTrie();
        Set<String> vocabulary = new HashSet<>();
        for (Product p : products) {
            for (String word : HtmlParser.tokenize(p.getSearchText())) {
                trie.insert(word);
                vocabulary.add(word);
            }
        }
        spellChecker = new SpellChecker(vocabulary);
        ranker = new PageRanker(index, products);
        deals = new DealComparator(products);
        System.out.println("Index ready: " + index.size() + " words, vocabulary: " + vocabulary.size() + " words");
    }

    // validate -> remember the search -> spell check unknown words -> rank pages
    private void searchProducts() {
        String query = readLine("Search for: ");
        if (!InputValidator.isValidKeyword(query)) {
            System.out.println("Use 2-60 letters, digits, spaces or hyphens.");
            return;
        }
        searchTracker.record(query.toLowerCase());

        List<String> words = HtmlParser.tokenize(query);
        for (String word : words) {
            if (!spellChecker.isKnown(word)) {
                List<String> suggestions = spellChecker.suggest(word, 2);
                if (!suggestions.isEmpty()) {
                    System.out.println("'" + word + "' not found. Did you mean: " + suggestions + "?");
                }
            }
        }

        List<Product> results = ranker.topPages(words, RESULTS_TO_SHOW);
        if (results.isEmpty()) {
            System.out.println("No products matched.");
            return;
        }
        int rank = 1;
        for (Product p : results) {
            System.out.println(rank++ + ". " + p);
        }
    }

    private void completeWord() {
        String prefix = readLine("Start typing a word: ");
        if (!InputValidator.isValidKeyword(prefix)) {
            System.out.println("Use 2-60 letters, digits, spaces or hyphens.");
            return;
        }
        List<String> words = trie.complete(prefix.toLowerCase(), RESULTS_TO_SHOW);
        System.out.println(words.isEmpty() ? "No completions found." : "Suggestions: " + words);
    }

    private void showTopSearches() {
        List<Map.Entry<String, Integer>> top = searchTracker.topSearches(RESULTS_TO_SHOW);
        if (top.isEmpty()) {
            System.out.println("Nothing searched yet.");
            return;
        }
        for (Map.Entry<String, Integer> entry : top) {
            System.out.println(entry.getKey() + " - searched " + entry.getValue() + " time(s)");
        }
    }

    private void showWordFrequency() {
        String url = readLine("Product page URL: ");
        if (!InputValidator.isValidUrl(url)) {
            System.out.println("That doesn't look like a link (it should start with http:// or https://).");
            return;
        }
        String word = readLine("Word to count (leave empty for the top words): ");
        if (word.isEmpty()) {
            List<Map.Entry<String, Integer>> top = frequencyCounter.topWords(url, RESULTS_TO_SHOW);
            System.out.println(top.isEmpty() ? "No words found for that page." : "Top words: " + top);
        } else if (InputValidator.isValidKeyword(word)) {
            System.out.println("'" + word + "' appears " + frequencyCounter.countInPage(url, word.toLowerCase())
                    + " time(s) on that page.");
        } else {
            System.out.println("Use 2-60 letters, digits, spaces or hyphens.");
        }
    }

    private void compareDeals() {
        String model = readLine("Model code (e.g. RTX-4070): ");
        if (!InputValidator.isValidKeyword(model)) {
            System.out.println("Use 2-60 letters, digits, spaces or hyphens.");
            return;
        }
        List<Product> offers = deals.offersFor(model);
        if (offers.isEmpty()) {
            System.out.println("No store lists that model.");
            return;
        }
        System.out.println("Best deal: " + offers.get(0));
        for (int i = 1; i < offers.size(); i++) {
            System.out.println("  also: " + offers.get(i));
        }
    }

    // each crawler saves its own CSV, so a failing store doesn't stop the others
    private void runCrawlers() {
        for (StoreCrawler crawler : crawlers) {
            System.out.println("Crawling " + crawler.storeName() + " ...");
            try {
                List<Product> found = crawler.crawl(PAGES_PER_CRAWL);
                if (found.isEmpty()) {
                    System.out.println("  nothing found, keeping the old file (if any)");
                    continue;
                }
                ProductCsv.write(PRODUCTS_FOLDER.resolve(crawler.storeName() + ".csv"), found);
                System.out.println("  saved " + found.size() + " products");
            } catch (IOException | RuntimeException e) {
                System.out.println("  " + crawler.storeName() + " failed: " + e.getMessage());
            }
        }
        loadProducts();
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!input.hasNextLine()) {
            return "0"; // input closed (Ctrl+Z / Ctrl+D): leave the menu
        }
        return input.nextLine().trim();
    }
}
