package com.webiq;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Application {
    private int maxDepth, maxTimeInMins;
    private WikiScraper scraper = new WikiScraper();
    private WikiPage mainPage;
    private LinkedHashMap<String, WikiPage> corpus = new LinkedHashMap<>();
    private ArrayList<WikiPage> urlList;

    public Application(String startingUrl, int maxDepth, int maxTimeInMins) {
        this.maxDepth = maxDepth;
        this.maxTimeInMins = maxTimeInMins;
        mainPage = scraper.generateWikiPageFromUrl(startingUrl);
        corpus.put(mainPage.getUrl(), mainPage);
    }

    public void runApplication() {
        constructCorpus();
        calculateTDIDF();
    }

    private void constructCorpus() {
        int start = 0, sizeBeforeExpansion;

        for (int depth = 1; depth <= maxDepth; depth++) {
            System.out.printf("Retrieving neighbors at depth %d\n", depth);
            urlList = new ArrayList<>(corpus.values());
            sizeBeforeExpansion = corpus.size();

            for (int idx = start; idx < sizeBeforeExpansion; idx++) {
                corpus.putAll(scraper.getNeighbors(urlList.get(idx), corpus));
            }

            start = sizeBeforeExpansion;

            for (WikiPage el : corpus.values()) {
                System.out.println(el.getUrl());
            }
        }
    }

    private void calculateTDIDF() {
        TFIDF tfidf = new TFIDF(mainPage, corpus);
        tfidf.calculateTFIDF();

        for (String el : tfidf.getTfidfScores().keySet()) {
            //System.out.println(mainPage.getBagOfWords().getOriginalWord(el));
            System.out.println(el);
        }
        //System.out.println(tfidf.getTfidfScores());
    }
}
