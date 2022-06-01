package com.webiq;

import java.util.HashMap;

public class BagOfWords {
    private final HashMap<String, Integer> bagOfWords;
    private int nTokens;

    public BagOfWords(String text) {
        this.bagOfWords = getBagOfWordsFromString(text);
    }

    // Generates a Bag-of-Words model from the input string
    public HashMap<String, Integer> getBagOfWordsFromString(String text) {
        text = cleanText(text);
        //System.out.printf("Cleaned page content: %s\n", text);

        HashMap<String, Integer> bagOfWords = new HashMap<>();
        String[] words = text.split("\\s+");
        nTokens = words.length;

        int count;

        for (String word : words) {
            // System.out.println(word);
            count = bagOfWords.getOrDefault(word, 0);
            bagOfWords.put(word, count + 1);
        }

        return bagOfWords;
    }

    /* Cleans the input text before generating the Bag-of-Words model
     by converting to lower case and removing all non-alphanumeric characters
    */
    private String cleanText(String text) {
        text = text.toLowerCase();
        // Use RegEx to remove all non-alphanumeric characters
        text = text.replaceAll("[^A-Za-z0-9 ]", "");

        return text;
    }

    public HashMap<String, Integer> getBagOfWords() {
        return bagOfWords;
    }

    public int getNTokens() {
        return nTokens;
    }
}
