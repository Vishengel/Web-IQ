package com.webiq;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;

public class WikiPage {
    private final String url, title, content;
    private HashMap<String, Integer> bagOfWords;
    private ArrayList<String> hyperlinks;

    public WikiPage(String url, String title, String content, ArrayList<String> hyperlinks) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.hyperlinks = hyperlinks;
        generateBagOfWords();
    }

    public HashMap<String, Integer> getBagOfWords() {
        return bagOfWords;
    }

    public void generateBagOfWords() {
        bagOfWords = Parser.getBagOfWordsFromString(content);
        //System.out.println(bagOfWords);
    }

    public ArrayList<String> getHyperlinks() {
        return hyperlinks;
    }

    public void setHyperlinks(ArrayList<String> hyperlinks) {
        this.hyperlinks = hyperlinks;
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
