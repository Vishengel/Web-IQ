package com.webiq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class WebIQ {

    public static void main(String[] args) {
        String startingUrl = "https://en.wikipedia.org/wiki/Groningen";
        int maxDepth = 2;

        WikiScraper scraper = new WikiScraper();
        WikiPage mainPage = scraper.generateWikiPageFromUrl(startingUrl);

        LinkedHashMap<String, WikiPage> corpus = new LinkedHashMap<>();
        ArrayList<WikiPage> urlList;
        corpus.put(mainPage.getUrl(), mainPage);

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

        TFIDF tfidf = new TFIDF(mainPage, corpus);
        tfidf.calculateTFIDF();

        for (String el : tfidf.getTfidfScores().keySet()) {
            //System.out.println(mainPage.getBagOfWords().getOriginalWord(el));
            System.out.println(el);
        }
        //System.out.println(tfidf.getTfidfScores());


    }

}
