package com.elib.domain;

import lombok.Getter;

@Getter
public enum ContentType {
    TEXT_XML("text/xml"), APPLICATION_JSON("application/json"), HTML("html");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public boolean isXml() {
        return this == TEXT_XML;
    }

    public boolean isJson() {
        return this == APPLICATION_JSON;
    }
}
