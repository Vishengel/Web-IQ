package com.webiq;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    private int maxDepth, nTopResults;
    private long maxTimeInMillis;
    private WikiScraper scraper = new WikiScraper();
    private WikiPage mainPage;
    private LinkedHashMap<String, WikiPage> corpus = new LinkedHashMap<>();
    private ArrayList<WikiPage> urlList;

    public Application(String startingUrl, int maxDepth, int maxTimeInMins, int nTopResults) {
        this.maxDepth = maxDepth;
        this.nTopResults = nTopResults;
        this.maxTimeInMillis = (long) maxTimeInMins*60*1000;
        mainPage = scraper.generateWikiPageFromUrl(startingUrl);
        corpus.put(mainPage.getUrl(), mainPage);
    }

    public void runApplication() {
        constructCorpus();
        calculateTDIDF();
    }

    private void constructCorpus() {
        int start = 0, sizeBeforeExpansion;
        long startTime = System.currentTimeMillis();

        for (int depth = 1; depth <= maxDepth; depth++) {
            System.out.printf("Retrieving neighbors at %d steps from the starting page\n", depth);
            urlList = new ArrayList<>(corpus.values());
            sizeBeforeExpansion = corpus.size();

            for (int idx = start; idx < sizeBeforeExpansion; idx++) {
                if ((System.currentTimeMillis() - startTime) > maxTimeInMillis) {
                    System.out.println("Max runtime for corpus construction reached. " +
                            "Starting calculation of most important words.");
                    return;
                }

                corpus.putAll(scraper.getNeighbors(urlList.get(idx), corpus));
            }

            start = sizeBeforeExpansion;

//            for (WikiPage el : corpus.values()) {
//                System.out.println(el.getUrl());
//            }
        }
        System.out.println("Max distance from starting page for corpus construction reached. " +
                "Starting calculation of most important words.");
    }

    private void calculateTDIDF() {
        TFIDF tfidf = new TFIDF(mainPage, corpus);
        tfidf.calculateTFIDF();

        List<String> nTopResultsList = tfidf.getTfidfScores().keySet().stream().limit(nTopResults).toList();

        for (String el : nTopResultsList) {
            System.out.println(el);
        }
    }
}
