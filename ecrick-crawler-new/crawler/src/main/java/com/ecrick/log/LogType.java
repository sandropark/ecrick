package com.ecrick.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogType {
    HC("healthCheck"),
    CR("crawl");

    private final String desc;
}
