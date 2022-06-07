package com.webiq;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.HashMap;

public class TextParser {
    private List<String> stoplist;

    public TextParser() {
        readStoplist();
    }

    // Read the list of stop words which is assumed to be in a file called stoplist.txt in the resources folder
    private void readStoplist() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("stoplist.txt")) {
            assert inputStream != null;
            stoplist = IOUtils.readLines(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Generates a Bag-of-Words model from the input string
    public BagOfWords getBagOfWordsFromString(String text) {
        HashMap<String, Integer> bagOfWords = new HashMap<>();
        String[] tokens = text.split("\\s+");
        int nTokens = tokens.length, count;

        for (String token : tokens) {
            String processedToken = cleanText(token);
            if (!stoplist.contains(processedToken)) {
                count = bagOfWords.getOrDefault(processedToken , 0);
                bagOfWords.put(processedToken , count + 1);
            }
        }

        return new BagOfWords(bagOfWords, nTokens);
    }

    /* Cleans the input text converting to lower case and removing all non-alphanumeric characters at
     * the beginning or the end of a word */
    private String cleanText(String text) {
        text = text.toLowerCase();
        // Use RegEx to remove all non-alphanumeric characters
        //text = text.replaceAll("[^A-Za-z0-9 ]", "");
        text = text.replaceAll("(^[^\\w]+)|([^\\w]+$)", "");

        return text;
    }
}
