package com.webiq;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class WikiScraper {
    public WikiScraper() {
        System.out.println("Initializing scraper");
    }

    /* Use Jsoup to retrieve the input Wikipedia page. Obtains the page's main text content + all hyperlinks and
     * creates a new WikiPage object with these elements */
    public void scrapePage(String url) {
        Document doc;

        System.out.printf("Scraping page %s\n", url);

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Select the div tag containing the main text content of the page
        Elements text = doc.select("div[id=mw-content-text]");
        String content = text.text();
        System.out.println(content);

        WikiPage wp = new WikiPage(url, doc.title(), content, getHyperLinksFromPage(doc));
        wp.printHyperlinks();
    }

    // Scrape a given page to retrieve all links to other Wikipedia pages
    private ArrayList<String> getHyperLinksFromPage(Document doc) {
        ArrayList<String> hyperlinks = new ArrayList<>();
        Elements links = doc.select("a[href]");
        /* We keep hyperlinks that match the following criteria: links to another English wiki article;
         * does not link to the same page (contains '#' after /wiki/); does not link to a meta-page such as a Talk page
         * (contains ':' after /wiki/); is not the main page of Wikipedia */
        String pattern = "https:\\/\\/en.wikipedia.org\\/wiki\\/(?!.*([:#]|\\bMain_Page\\b)).*";

        System.out.printf("\nLinks: (%d)", links.size());
        for (Element link : links) {
            // Add link if it matches the pattern above and if it's not already in the list of hyperlinks
            if (link.attr("abs:href").matches(pattern) && !hyperlinks.contains(link.attr("abs:href"))) {
                hyperlinks.add(link.attr("abs:href"));
            }
            //System.out.printf(" * a: <%s>  (%s) -", link.attr("abs:href"), link.text());
            //System.out.printf("Match: %b\n", link.attr("abs:href").matches(pattern));
        }

        return hyperlinks;
    }

}
