package com.webiq;

public class WebIQMain {

    public static void main(String[] args) {
        String startingUrl = "https://en.wikipedia.org/wiki/Elephant";
        int maxDepth = 1, maxTimeInMinutes = 2;

        Application app = new Application(startingUrl, maxDepth, maxTimeInMinutes);
        app.runApplication();
    }

}
