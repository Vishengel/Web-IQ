package com.webiq;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WikiScraper {

    public WikiScraper() {
        System.out.println("Initializing scraper");
    }

    /* Use Jsoup to retrieve the input Wikipedia page. Obtains the page's main text content + all hyperlinks and
    creates a new WikiPage object with these elements */
    public void scrapePage(String url) {
        Document doc;

        System.out.printf("Scraping page %s\n", url);

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements text = doc.select("div[id=mw-content-text]");
        String content = text.text();

        System.out.println(content);

        WikiPage wp = new WikiPage(content);

        // ToDo: Scrape all hyperlinks, send to wp
    }

}
