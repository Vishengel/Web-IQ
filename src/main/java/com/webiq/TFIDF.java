package com.webiq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TFIDF {
    private WikiPage wp;
    private ArrayList<WikiPage> corpus;
    private HashMap<String, Double> tfidfScores;

    public TFIDF(WikiPage wp, ArrayList<WikiPage> corpus) {
        this.wp = wp;
        this.corpus = corpus;
        this.tfidfScores = new HashMap<>();
    }

    public void calculateTFIDF() {
        double tf, idf;
        HashMap<String, Double> unsortedTfidfScores = new HashMap<>();

        for (String word : wp.getBagOfWords().getBagOfWords().keySet()) {
            tf = calculateTF(word);
            idf = calculateIDF(word);
            System.out.printf("%s: %.5f\n", word, tf*idf);
            unsortedTfidfScores.put(word, tf*idf);
        }

        tfidfScores = sortMapByValueDescending(unsortedTfidfScores);
    }

    private double calculateTF(String word) {
        return (double) wp.getBagOfWords().getBagOfWords().get(word) / (double) wp.getBagOfWords().getNTokens();
    }

    private double calculateIDF(String word) {
        int docOccurrences = 0;

        for (WikiPage page : corpus) {
            if (page.getBagOfWords().getBagOfWords().containsKey(word)) {
                docOccurrences++;
            }
        }

        return Math.log((double) corpus.size() / (1.0 + (double) docOccurrences));
    }

    private HashMap<String, Double> sortMapByValueDescending(HashMap<String, Double> hashMap) {
        HashMap<String, Double> sortedHashMap = new LinkedHashMap<String, Double>();

        hashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedHashMap.put(x.getKey(), x.getValue()));

        return sortedHashMap;
    }

    public HashMap<String, Double> getTfidfScores() {
        return tfidfScores;
    }
}
