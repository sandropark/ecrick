package com.ecrick.dto;

import lombok.AllArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@AllArgsConstructor
public class ResponseBodyDto {
    private Object response;

    public static ResponseBodyDto from(Object response) {
        return new ResponseBodyDto(response);
    }

    public String getBody() {
        if (response instanceof Connection.Response)
            return ((Connection.Response) response).body();
        if (response instanceof ResponseEntity)
            return ((ResponseEntity<String>) response).getBody();
        throw new RuntimeException();
    }

    public Document getHtml() {
        if (response instanceof Connection.Response) {
            try {
                return ((Connection.Response) response).parse();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException();
    }

}

