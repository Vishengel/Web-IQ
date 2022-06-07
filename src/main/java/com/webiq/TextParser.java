package com.webiq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.HashMap;

public class TextParser {
    private final Path stoplistPath = Paths.get("src/main/resources/stoplist.txt");
    private List<String> stoplist;

    public TextParser() {
        readStoplist();
    }

    private void readStoplist() {
        try {
            stoplist = Files.readAllLines(stoplistPath, StandardCharsets.UTF_8);
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

    // Cleans the input text converting to lower case and removing all non-alphanumeric characters
    private String cleanText(String text) {
        text = text.toLowerCase();
        // Use RegEx to remove all non-alphanumeric characters
        //text = text.replaceAll("[^A-Za-z0-9 ]", "");
        text = text.replaceAll("(^[^\\w]+)|([^\\w]+$)", "");

        return text;
    }
}
