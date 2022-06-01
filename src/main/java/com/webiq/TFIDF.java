package com.webiq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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

    public void calculate_TFIDF() {
        double tf, idf;
        HashMap<String, Double> unsortedTfidfScores = new HashMap<>();

        for (String word : wp.getBagOfWords().getBagOfWords().keySet()) {
            tf = calculate_TF(word);
            idf = calculate_IDF(word);
            System.out.printf("%s: %.5f\n", word, tf*idf);
            unsortedTfidfScores.put(word, tf*idf);
        }

        tfidfScores = sortByValue(unsortedTfidfScores);
    }

    private double calculate_TF(String word) {
        return (double) wp.getBagOfWords().getBagOfWords().get(word) / (double) wp.getBagOfWords().getNTokens();
    }

    private double calculate_IDF(String word) {
        int docOccurrences = 0;

        for (WikiPage page : corpus) {
            if (page.getBagOfWords().getBagOfWords().containsKey(word)) {
                docOccurrences++;
            }
        }

        return Math.log((double) corpus.size() / (1.0 + (double) docOccurrences));
    }

    private HashMap<String, Double> sortByValue(HashMap<String, Double> hashMap) {
        /*
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list
                = new LinkedList<Map.Entry<String, Double> >(
                hashMap.entrySet());

        // Sort the list using lambda expression
        Collections.sort(
                list,
                (i1,
                 i2) -> i1.getValue().compareTo(i2.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Double> sortedHashMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> el : list) {
            sortedHashMap.put(el.getKey(), el.getValue());
        }
        */

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
