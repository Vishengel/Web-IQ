package com.webiq;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Application {
    private final int maxDepth, nTopResults;
    private final long maxTimeInMillis;
    private final WikiScraper scraper = new WikiScraper();
    private final WikiPage mainPage;
    private final LinkedHashMap<String, WikiPage> corpus = new LinkedHashMap<>();

    public Application(String startingUrl, int maxDepth, int maxTimeInMins, int nTopResults) {
        this.maxDepth = maxDepth;
        this.nTopResults = nTopResults;
        this.maxTimeInMillis = (long) maxTimeInMins*60*1000;
        // Generate a Wikipage object from the input page, and put it in the corpus hashmap
        mainPage = scraper.generateWikiPageFromUrl(startingUrl);
        corpus.put(mainPage.getUrl(), mainPage);
    }

    /* The main logic of the program. Generate a corpus of Wikipedia pages adjacent to the input page.
     * Calculate the TF-IDF score for each (applicable) word in the input page, and print the top N results */
    public void runApplication() {
        constructCorpus();
        TFIDF tfidf = new TFIDF(mainPage, corpus);
        tfidf.calculateTFIDF();
        tfidf.printNTopResults(nTopResults);
    }

    /* Generate a corpus of Wikipedia pages adjacent to the input page. At depth 1, a Wikipage is created and added
     * to the corpus for each Wikipedia page that is linked to from the input page. At depth 2, a Wikipage is created
     * for each Wikipedia page that is linked to from each page added at depth 1, etc.
     * Stops when either the maximum depth or the time limit provided has been reached */
    private void constructCorpus() {
        int start = 0, sizeBeforeExpansion;
        long startTime = System.currentTimeMillis();
        ArrayList<WikiPage> pageList;

        for (int depth = 1; depth <= maxDepth; depth++) {
            System.out.printf("Retrieving neighbors at %d %s from the starting page\n",
                    depth, depth == 1 ? "step" : "steps");
            pageList = new ArrayList<>(corpus.values());
            sizeBeforeExpansion = corpus.size();

            for (int idx = start; idx < sizeBeforeExpansion; idx++) {
                // Stop corpus construction of the max time limit has been reached.
                if ((System.currentTimeMillis() - startTime) > maxTimeInMillis) {
                    System.out.println("Max runtime for corpus construction reached. " +
                            "Starting calculation of most important words.");
                    return;
                }

                corpus.putAll(scraper.getNeighbors(pageList.get(idx), corpus));
            }

            start = sizeBeforeExpansion;
        }
        // If we reach this part, we've parsed all pages up until the maximum depth
        System.out.println("Max distance from starting page for corpus construction reached. " +
                "Starting calculation of most important words.");
    }
}
