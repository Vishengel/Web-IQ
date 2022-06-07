package com.webiq;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.HashMap;

public class TextParser {
    // A list of very common words in the English language that we don't want to have in our results
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
    // ToDo: this TF-IDF-based method only uses unigrams (e.g. single tokens). This does not take into account that some tokens semantically belong together, e.g. "Open-source intelligence".
    // Generates a Bag-of-Words model from the input string
    public BagOfWords getBagOfWordsFromString(String text) {
        HashMap<String, Integer> bagOfWords = new HashMap<>();
        // Cut the input text into tokens by splitting on whitespace characters
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
    // ToDo: cleaner results could be obtained by using a stemmer or a lemmatizer to group derivations of the same token together (e.g. car, cars, and car's)
    /* Cleans the input text converting to lower case and removing all non-alphanumeric characters at
     * the beginning or the end of a word. This keeps tokens such as 9/11 or open-source intact. */
    private String cleanText(String text) {
        text = text.toLowerCase();
        /* Use RegEx to remove all non-alphanumeric characters at
         * the beginning or the end of a word */
        text = text.replaceAll("(^[^\\w]+)|([^\\w]+$)", "");

        return text;
    }
}
