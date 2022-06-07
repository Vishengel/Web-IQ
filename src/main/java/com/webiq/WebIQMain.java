package com.webiq;

public class WebIQMain {

    public static void main(String[] args) {
        String inputUrl;
        int maxDepth, maxTimeInMinutes, nTopResults;

        // ToDo: not the neatest way of handling CLI arguments, but works for the purposes of this program
        /* Read the input page, the maximum number of steps, the maximum runtime in minutes and the amount
         * of results to display from the input arguments if possible, otherwise use the default settings */
        if (args.length == 4) {
            inputUrl = "https://en.wikipedia.org/wiki/" + args[0];
            maxDepth = Integer.parseInt(args[1]);
            maxTimeInMinutes = Integer.parseInt(args[2]);
            nTopResults = Integer.parseInt(args[3]);
        } else {
            inputUrl = "https://en.wikipedia.org/wiki/Open-source_intelligence";
            maxDepth = 2;
            maxTimeInMinutes = 5;
            nTopResults = 25;
        }

        System.out.printf("Finding the %d most relevant words on the Wikipedia page %s according to their TF-IDF scores\n"
                , nTopResults, inputUrl);
        Application app = new Application(inputUrl, maxDepth, maxTimeInMinutes, nTopResults);
        app.runApplication();
    }

}
