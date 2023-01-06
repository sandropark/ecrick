package com.elib.crawler.parser;

public class BodyCleaner {
    protected String clean(String body) {
        return body
                .replaceAll("&gt;|&lt;|&#39;|&#40;|&#41;|&amp;", "")
                .replaceAll("&?quot;", "'");
    }
}
