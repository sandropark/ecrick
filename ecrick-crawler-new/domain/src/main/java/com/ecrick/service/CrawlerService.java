package com.ecrick.service;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import com.ecrick.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CrawlerService {
    private final LibraryRepository libraryRepository;
    private final Crawler crawler;

    public void crawl() {
        List<Library> libraries = libraryRepository.findAll();

        CrawlerModel crawlerModel = crawler.healthCheck(libraries.get(0));

        int totalBooks = crawlerModel.getTotalBooks();
        List<RowBook> rowBooks = crawlerModel.getRowBooks();
    }

}
