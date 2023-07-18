package com.elib.crawler.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class CrawlerLibraryServiceTest {

    RestTemplate template = new RestTemplate();

//    String url = "http://ebook.gangnam.go.kr/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?";
    String url = "https://ebook.bcl.go.kr:444/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?";

    @Test
    void test() throws Exception {
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        HttpStatus statusCode = response.getStatusCode();
        System.out.println("statusCode = " + statusCode);
    }

}