package com.ecrick.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentType {
    TEXT_XML("text/xml", ".xml"), APPLICATION_JSON("application/json", ".json"), HTML("html", ".html");

    private final String value;
    private final String extension;

    public boolean isXml() {
        return this == TEXT_XML;
    }

    public boolean isJson() {
        return this == APPLICATION_JSON;
    }
}
