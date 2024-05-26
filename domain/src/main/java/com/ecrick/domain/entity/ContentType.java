package com.ecrick.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentType {
    TEXT_XML("text/xml"),
    APPLICATION_JSON("application/json"),
    HTML("html");

    private final String value;

    public boolean isXml() {
        return this == TEXT_XML;
    }

    public boolean isJson() {
        return this == APPLICATION_JSON;
    }
}
