package com.webiq;

import java.util.*;

public class TFIDF {
    private final WikiPage startingPage;
    private final HashMap<String, WikiPage> corpus;
    private HashMap<String, Double> tfidfScores;

    public TFIDF(WikiPage startingPage, HashMap<String, WikiPage> corpus) {
        this.startingPage = startingPage;
        this.corpus = corpus;
        this.tfidfScores = new HashMap<>();
    }

    public void calculateTFIDF() {
        double tf, idf;
        HashMap<String, Double> unsortedTfidfScores = new HashMap<>();

        for (String word : startingPage.getBagOfWords().getBagOfWords().keySet()) {
            tf = calculateTF(word);
            idf = calculateIDF(word);
            unsortedTfidfScores.put(word, tf*idf);
        }

        tfidfScores = sortMapByValueDescending(unsortedTfidfScores);
    }

    private double calculateTF(String word) {
        return (double) startingPage.getBagOfWords().getBagOfWords().get(word) / (double) startingPage.getBagOfWords().getNTokens();
    }

    private double calculateIDF(String word) {
        int docOccurrences = 0;

        for (WikiPage page : corpus.values()) {
            if (page.getBagOfWords().getBagOfWords().containsKey(word)) {
                docOccurrences++;
            }
        }

        return Math.log((double) corpus.size() / (1.0 + (double) docOccurrences));
    }

    private HashMap<String, Double> sortMapByValueDescending(HashMap<String, Double> hashMap) {
        HashMap<String, Double> sortedHashMap = new LinkedHashMap<>();

        hashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedHashMap.put(x.getKey(), x.getValue()));

        return sortedHashMap;
    }

    public HashMap<String, Double> getTfidfScores() {
        return tfidfScores;
    }

    public void printNTopResults(int nTopResults) {
        List<String> nTopResultsList = tfidfScores.keySet().stream().limit(nTopResults).toList();
        int count = 1;

        for (String el : nTopResultsList) {
            System.out.printf("%d: %s\n",count++, el);
        }
    }
}
