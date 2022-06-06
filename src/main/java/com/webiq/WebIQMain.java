package com.webiq;

public class WebIQMain {

    public static void main(String[] args) {
        String startingUrl;
        int maxDepth, maxTimeInMinutes, nTopResults;

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
