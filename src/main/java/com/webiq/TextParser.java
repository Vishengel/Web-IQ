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
        //text = cleanText(text);
        //System.out.printf("Cleaned page content: %s\n", text);

        HashMap<String, Integer> bagOfWords = new HashMap<>();

        String[] words = text.split("\\s+");
        int nTokens = words.length;

        int count;

        for (String word : words) {
            // System.out.println(word);
            String processedWord = cleanText(word);
            if (!stoplist.contains(word)) {
                count = bagOfWords.getOrDefault(processedWord , 0);
                bagOfWords.put(processedWord , count + 1);
                //dictionary.put(processedWord, word);
            }
        }

        return new BagOfWords(bagOfWords, nTokens);
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
}
