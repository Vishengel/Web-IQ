package com.webiq;

import java.util.*;

/* Class that handles the calculation and printing of the
Term frequency–inverse document frequency scores of each word in the input page */
public class TFIDF {
    private final WikiPage inputPage;
    private final HashMap<String, WikiPage> corpus;
    private HashMap<String, Double> tfidfScores;

    public TFIDF(WikiPage inputPage, HashMap<String, WikiPage> corpus) {
        this.inputPage = inputPage;
        this.corpus = corpus;
        this.tfidfScores = new HashMap<>();
    }

    public void calculateTFIDF() {
        double tf, idf;
        HashMap<String, Double> unsortedTfidfScores = new HashMap<>();

        for (String token : inputPage.getBagOfWords().getBagOfWords().keySet()) {
            tf = calculateTF(token);
            idf = calculateIDF(token);
            // Calculate the TD-IDF score as the product of tf and idf
            unsortedTfidfScores.put(token, tf*idf);
        }

        // Sort the
        tfidfScores = sortHashmapByValueDescending(unsortedTfidfScores);
    }

    /* Calculate the term frequency of an input token, which is given by the raw frequency of the token in the input page,
     * divided by the total amount of tokens in the input page */
    private double calculateTF(String token) {
        // Division by 0 will not happen, since this method will not be called if the input page has no tokens
        return (double) inputPage.getBagOfWords().getBagOfWords().get(token) / (double) inputPage.getBagOfWords().getNTokens();
    }

    /* Calculate the inverse focument frequency of an input token, which is given by natural logarith of the total
     *  number of pages in the corpus, divided by the total amount of pages in the corpus the token appears in. */
    private double calculateIDF(String token) {
        int docOccurrences = 0;

        for (WikiPage page : corpus.values()) {
            if (page.getBagOfWords().getBagOfWords().containsKey(token)) {
                docOccurrences++;
            }
        }
        // Division by 0 will not happen, as the input page (which contains the token by definition) is part of the corpus
        return Math.log((double) corpus.size() / (double) docOccurrences);
    }

    /* Takes a hashmap as input, sorts it by value (descending) and returns the sorted hashmap.
     * Could be made a static method in a separate utility class, but it's only used here anyway */
    private HashMap<String, Double> sortHashmapByValueDescending(HashMap<String, Double> hashMap) {
        HashMap<String, Double> sortedHashMap = new LinkedHashMap<>();

        hashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedHashMap.put(x.getKey(), x.getValue()));

        return sortedHashMap;
    }

    // Print the N tokens with the highest TD-IDF scores
    public void printNTopResults(int nTopResults) {
        List<String> nTopResultsList = tfidfScores.keySet().stream().limit(nTopResults).toList();
        int count = 1;

        for (String el : nTopResultsList) {
            System.out.printf("%d: %s\n", count++, el);
        }
    }
}
