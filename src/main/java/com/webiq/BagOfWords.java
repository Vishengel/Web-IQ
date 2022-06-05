package com.webiq;

import java.util.HashMap;

public class BagOfWords {
    private HashMap<String, Integer> bagOfWords;
    private HashMap<String, String> dictionary;
    private int nTokens;

    public BagOfWords(HashMap<String, Integer> bagOfWords, int nTokens) {
        this.bagOfWords = bagOfWords;
        this.nTokens = nTokens;
    }

    // Generates a Bag-of-Words model from the input string
    public HashMap<String, Integer> getBagOfWordsFromString(String text) {
        //text = cleanText(text);
        //System.out.printf("Cleaned page content: %s\n", text);

        bagOfWords = new HashMap<>();
        dictionary = new HashMap<>();

        String[] words = text.split("\\s+");
        nTokens = words.length;

        int count;

        for (String word : words) {
            // System.out.println(word);
            String processedWord = cleanText(word);
            count = bagOfWords.getOrDefault(processedWord , 0);
            bagOfWords.put(processedWord , count + 1);
            dictionary.put(processedWord, word);
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

    public String getOriginalWord(String processedWord) {
        return dictionary.get(processedWord);
    }

    public int getNTokens() {
        return nTokens;
    }
}
