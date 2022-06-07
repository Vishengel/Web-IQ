package com.webiq;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class WikiScraper {
    private final TextParser tp;

    public WikiScraper() {
        tp = new TextParser();
    }

    /* Use Jsoup to retrieve the input Wikipedia page. Obtains the page's main text content + all hyperlinks and
     * creates a new WikiPage object with these elements */
    public WikiPage generateWikiPageFromUrl(String url) {
        Document doc;
        // ToDo: an external library (JSoup) is used for HTML parsing
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Select the div tag containing the main text content of the page
        Elements mainTextContent = doc.select("div[id=mw-content-text]");
        // Remove the metadata tables ("This article has multiple issues", "This article needs to be updated" etc.)
        doc.select("table.metadata").remove();
        // Remove the IEEE-style citations (i.e. [12]) that occur in the text
        doc.select("sup.reference").remove();
        // Remove the list of references, as these tend to muddle the results
        doc.select("div.reflist").remove();
        // Remove the [edit] buttons that occur after headlines
        doc.select("span.mw-editsection").remove();
        /* Remove access dates that come with citations and references, since they tend to end up in the results:
         * multiple refs tend to have the same access date, which provides no semantic information on the page subject */
        doc.select("span.reference-accessdate").remove();

        String content = mainTextContent.text();

        return new WikiPage(url, doc.title(), content, tp.getBagOfWordsFromString(content), getHyperLinksFromPage(doc));
    }

    // Scrape a given page to retrieve all links to other Wikipedia pages
    private ArrayList<String> getHyperLinksFromPage(Document doc) {
        ArrayList<String> hyperlinks = new ArrayList<>();
        Elements links = doc.select("a[href]");
        /* We keep hyperlinks that match the following criteria: links to another English wiki article;
         * does not link to the same page (contains '#' after /wiki/); does not link to a meta-page such as a Talk page
         * (i.e. does not contain a ':' after /wiki/); is not the main page of Wikipedia */
        String pattern = "https:\\/\\/en.wikipedia.org\\/wiki\\/(?!.*([:#]|\\bMain_Page\\b)).*";

        for (Element link : links) {
            // Add link if it matches the pattern above and if it's not already in the list of hyperlinks
            if (link.attr("abs:href").matches(pattern)
                    && !hyperlinks.contains(link.attr("abs:href"))) {
                hyperlinks.add(link.attr("abs:href"));
            }
        }

        return hyperlinks;
    }

    /* Takes a Wikipage object and a corpus HashMap as inputs. Returns a LinkedHashMap of "neighbors" of the input
     * Wikipage, i.e. all other English-language Wikipedia pages the input pages links to. The corpus is used to prevent
     * the method from generating Wikipage objects that are already part of the corpus */
    public LinkedHashMap<String, WikiPage> getNeighbors(WikiPage wp, HashMap<String, WikiPage> corpus) {
        int count = 1;
        LinkedHashMap<String, WikiPage> neighbors = new LinkedHashMap<>();
        WikiPage neighbor;

        for (String link : wp.getHyperlinks()) {
            System.out.printf("Parsing neighbor %s/%s of page %s\r", count, wp.getHyperlinks().size(), wp.getUrl());
            // Don't bother generating a Wikipage for a page that is already in the corpus
            if (!corpus.containsKey(link)) {
                neighbor = generateWikiPageFromUrl(link);
                neighbors.put(neighbor.getUrl(), neighbor);
            }
            count++;
        }
        System.out.println();

        return neighbors;
    }
}
