package com.elib.crawler.service;

import com.elib.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class CrawlerLibraryService {

    private final LibraryRepository libraryRepository;
    private RestTemplate template = new RestTemplate();

}
