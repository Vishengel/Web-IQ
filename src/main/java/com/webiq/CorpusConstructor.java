package com.webiq;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CorpusConstructor {
    private final WikiPage inputPage;
    private final LinkedHashMap<String, WikiPage> corpus = new LinkedHashMap<>();
    private final WikiScraper scraper = new WikiScraper();
    private final int maxDepth, maxTimeInMillis;

    public CorpusConstructor(String inputUrl, int maxDepth, int maxTimeInMins) {
        // Generate a Wikipage object from the input page, and put it in the corpus hashmap
        this.inputPage = scraper.generateWikiPageFromUrl(inputUrl);
        corpus.put(inputPage.getUrl(), inputPage);

        this.maxDepth = maxDepth;
        this.maxTimeInMillis = maxTimeInMins*60*1000;
    }

    /* Generate a corpus of Wikipedia pages adjacent to the input page. At depth 1, a Wikipage is created and added
     * to the corpus for each Wikipedia page that is linked to from the input page. At depth 2,
     * a Wikipage is created for each Wikipedia page that is linked to from each page added at depth 1, etc. */
    public LinkedHashMap<String, WikiPage> constructCorpus() {
        /* These two ints are used to make sure that at each iteration of the main for-loop,
         * only the neighbors are retrieved of pages that were added in the previous iteration */
        int start = 0, sizeBeforeExpansion;
        long startTime = System.currentTimeMillis();
        ArrayList<WikiPage> pageList;

        for (int depth = 1; depth <= maxDepth; depth++) {
            System.out.printf("Retrieving neighbors at %d %s from the starting page\n",
                    depth, depth == 1 ? "step" : "steps");
            // Create list of all Wikipages in the corpus to iterate through
            pageList = new ArrayList<>(corpus.values());
            sizeBeforeExpansion = corpus.size();

            for (int idx = start; idx < sizeBeforeExpansion; idx++) {
                /* Stop corpus construction of the max time limit has been reached. It gets called once per loop,
                 * so the method only returns after all neighbors from the previous iteration have been added */
                if ((System.currentTimeMillis() - startTime) > maxTimeInMillis) {
                    System.out.println("Max runtime for corpus construction reached. " +
                            "Starting calculation of most important words.");
                    return corpus;
                }
                // Retrieve all neighbors of the current page and add them to the corpus
                corpus.putAll(scraper.getNeighbors(pageList.get(idx), corpus));
            }

            start = sizeBeforeExpansion;
        }
        // If we reach this part, we've parsed all pages up until the maximum depth
        System.out.println("Max distance from starting page for corpus construction reached. " +
                "Starting calculation of most important words.");
        return corpus;
    }

    public WikiPage getInputPage() {
        return inputPage;
    }
}
