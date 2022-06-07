package com.webiq;

public class WebIQMain {

    public static void main(String[] args) {
        String startingUrl;
        int maxDepth, maxTimeInMinutes, nTopResults;

        /* Read the input page, the maximum number of steps, the maximum runtime and the amount of results to display
         * from the input arguments if possible, otherwise use the default settings */
        if (args.length == 4) {
            startingUrl = "https://en.wikipedia.org/wiki/" + args[0];
            maxDepth = Integer.parseInt(args[1]);
            maxTimeInMinutes = Integer.parseInt(args[2]);
            nTopResults = Integer.parseInt(args[3]);
        } else {
            startingUrl = "https://en.wikipedia.org/wiki/Amsterdam";
            maxDepth = 1;
            maxTimeInMinutes = 5;
            nTopResults = 50;
        }

        Application app = new Application(startingUrl, maxDepth, maxTimeInMinutes, nTopResults);
        app.runApplication();
    }

}
