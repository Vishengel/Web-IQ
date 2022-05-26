package com.webiq;

import java.util.HashMap;

public class WikiPage {
    HashMap<String, Integer> bagOfWords;
    public WikiPage(String content) {
        bagOfWords = Parser.getBagOfWords(content);
        System.out.println(bagOfWords);
    }
}
