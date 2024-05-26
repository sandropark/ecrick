package com.ecrick.crawler.infra.utils;

public class BodyCleanUtils {
    public static String clean(String body) {
        return body
                .replaceAll("&gt;|&lt;|&#39;|&#40;|&#41;|&amp;", "")
                .replaceAll("&?quot;", "'");
    }
}
