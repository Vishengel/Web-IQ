package com.webiq;

public class Application {
    private final int nTopResults;
    private final CorpusConstructor corpusConstructor;

    public Application(String inputUrl, int maxDepth, int maxTimeInMins, int nTopResults) {
        this.nTopResults = nTopResults;
        this.corpusConstructor = new CorpusConstructor(inputUrl, maxDepth, maxTimeInMins);
    }

    /* The main logic of the program. Generate a corpus of Wikipedia pages adjacent to the input page.
     * Calculate the TF-IDF score for each (applicable) word in the input page, and print the top N results */
    public void runApplication() {
        TFIDF tfidf = new TFIDF(corpusConstructor.getInputPage(), corpusConstructor.constructCorpus());
        tfidf.calculateTFIDF();
        tfidf.printNTopResults(nTopResults);
    }
}
