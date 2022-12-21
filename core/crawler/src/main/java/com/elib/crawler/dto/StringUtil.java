package com.elib.crawler.dto;

public abstract class StringUtil {
    protected String clean(String value) {
        return value.strip().replaceAll("&.+;", "");
    }

    protected String cleanAuthor(String value) {
        return clean(value).replaceAll("\s저$", "");
    }
}
