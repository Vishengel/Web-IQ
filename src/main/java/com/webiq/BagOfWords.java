package com.webiq;

import java.util.HashMap;

/* Small class that contains a HashMap of tokens and their frequencies on a given page,
 * plus the total amount of tokens in the bag-of-words model */
public class BagOfWords {
    private final HashMap<String, Integer> bagOfWords;
    private final int nTokens;

    public BagOfWords(HashMap<String, Integer> bagOfWords, int nTokens) {
        this.bagOfWords = bagOfWords;
        this.nTokens = nTokens;
    }

    public HashMap<String, Integer> getBagOfWords() {
        return bagOfWords;
    }

    public int getNTokens() {
        return nTokens;
    }
}
