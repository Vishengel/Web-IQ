package com.webiq;

import java.util.ArrayList;

public class WebIQ {

    public static void main(String[] args) {
        String startingUrl = "https://en.wikipedia.org/wiki/Amsterdam";
        WikiScraper scraper = new WikiScraper();
        WikiPage mainPage = scraper.generateWikiPageFromUrl(startingUrl);

        ArrayList<WikiPage> mainPageNeighbors = scraper.getNeighbors(mainPage);
        TFIDF tfidf = new TFIDF(mainPage, mainPageNeighbors);
        tfidf.calculateTFIDF();

        for (String el : tfidf.getTfidfScores().keySet()) {
            //System.out.println(mainPage.getBagOfWords().getOriginalWord(el));
            System.out.println(el);
        }
        //System.out.println(tfidf.getTfidfScores());


    }

}
