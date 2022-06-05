package com.webiq;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class WikiScraper {
    private final TextParser tp;

    public WikiScraper() {
        tp = new TextParser();
    }

    /* Use Jsoup to retrieve the input Wikipedia page. Obtains the page's main text content + all hyperlinks and
     * creates a new WikiPage object with these elements */
    public WikiPage generateWikiPageFromUrl(String url) {
        Document doc;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Select the div tag containing the main text content of the page
        Elements text = doc.select("div[id=mw-content-text]");
        // Remove the metadata tables ("This article has multiple issues", "This article needs to be updated" etc.)
        doc.select("table.metadata").remove();
        // Remove the IEEE-style citations (i.e. [12]) that occur in the text
        doc.select("class.reference").remove();
        // Remove the list of references, as these tend to muddle the results
        doc.select("div.reflist").remove();
        // Remove the [edit] buttons that occur after headlines
        doc.select("span.mw-editsection").remove();

        String content = text.text();
        //System.out.println(content);

        //wp.printHyperlinks();
        return new WikiPage(url, doc.title(), content, tp.getBagOfWordsFromString(content), getHyperLinksFromPage(doc));
    }

    // Scrape a given page to retrieve all links to other Wikipedia pages
    private ArrayList<String> getHyperLinksFromPage(Document doc) {
        ArrayList<String> hyperlinks = new ArrayList<>();
        Elements links = doc.select("a[href]");
        /* We keep hyperlinks that match the following criteria: links to another English wiki article;
         * does not link to the same page (contains '#' after /wiki/); does not link to a meta-page such as a Talk page
         * (contains ':' after /wiki/); is not the main page of Wikipedia */
        String pattern = "https:\\/\\/en.wikipedia.org\\/wiki\\/(?!.*([:#]|\\bMain_Page\\b)).*";

        //System.out.printf("\nLinks: (%d)", links.size());
        for (Element link : links) {
            // Add link if it matches the pattern above and if it's not already in the list of hyperlinks
            if (link.attr("abs:href").matches(pattern)
                    && !hyperlinks.contains(link.attr("abs:href"))) {
                hyperlinks.add(link.attr("abs:href"));
                //System.out.println(link.attr("abs:href"));
            }
            //System.out.printf(" * a: <%s>  (%s) -", link.attr("abs:href"), link.text());
            //System.out.printf("Match: %b\n", link.attr("abs:href").matches(pattern));
        }

        return hyperlinks;
    }

    public ArrayList<WikiPage> getNeighbors(WikiPage wp) {
        int count = 1;
        ArrayList<WikiPage> neighbors = new ArrayList<>();

        for (String link : wp.getHyperlinks()) {
            System.out.printf("Retrieving neighbor %s/%s\r", count, wp.getHyperlinks().size());
            neighbors.add(generateWikiPageFromUrl(link));
            count++;
        }

        return neighbors;
    }
}
