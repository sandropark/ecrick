package com.ecrick.crawler.domain.webclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RequestFailure {
    private String url;
    private String exMessage;

    public void print() {
        System.out.println("url = " + url + ", message = " + exMessage);
    }
}
