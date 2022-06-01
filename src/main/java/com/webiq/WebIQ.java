package com.webiq;

import java.util.ArrayList;

public class WebIQ {

    public static void main(String[] args) {
        // ToDo Only read main body of next from Wiki page (no References and such)
        String startingUrl = "https://en.wikipedia.org/wiki/Groningen";
        WikiScraper scraper = new WikiScraper();
        WikiPage mainPage = scraper.generateWikiPageFromUrl(startingUrl);
        ArrayList<WikiPage> mainPageNeighbors = scraper.getNeighbors(mainPage);
        TFIDF tfidf = new TFIDF(mainPage, mainPageNeighbors);
        tfidf.calculate_TFIDF();

        for (String el : tfidf.getTfidfScores().keySet()) {
            System.out.println(el);
        }
        //System.out.println(tfidf.getTfidfScores());
    }

}
