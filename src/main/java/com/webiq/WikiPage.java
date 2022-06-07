package com.webiq;

import java.util.ArrayList;

/* Class to store all relevant info of a Wikipedia page: its url, the page title, the main text content,
 * the bag of words model created from the text content, and a list of links to other Wikipedia pages that occur
 * on this particular page */
public class WikiPage {
    private final String url, title, content;
    private final BagOfWords bagOfWords;
    private final ArrayList<String> hyperlinks;

    public WikiPage(String url, String title, String content, BagOfWords bagOfWords, ArrayList<String> hyperlinks) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.bagOfWords = bagOfWords;
        this.hyperlinks = hyperlinks;
    }

    public String getUrl() {
        return url;
    }

    public BagOfWords getBagOfWords() {
        return bagOfWords;
    }

    public ArrayList<String> getHyperlinks() {
        return hyperlinks;
    }

    // Below are some unused print methods
    public void printHyperlinks() {
        for (String link : hyperlinks) {
            System.out.println(link);
        }
    }

    @Override
    public String toString() {
        return "WikiPage{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public void printMetaData() {
        System.out.printf("Url: %s\n", url);
        System.out.printf("Page title: %s\n", title);
    }
}
