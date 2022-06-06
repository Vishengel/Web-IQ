package com.webiq;

import java.util.ArrayList;

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
