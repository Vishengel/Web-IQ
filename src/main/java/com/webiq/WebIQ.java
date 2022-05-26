package com.webiq;

public class WebIQ {

    public static void main(String[] args) {
        String startingPage = "https://en.wikipedia.org/wiki/Open-source_intelligence";
        WikiScraper scraper = new WikiScraper();

        scraper.scrapePage(startingPage);
    }

}
