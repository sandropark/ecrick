package sandro.elib.elib.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sandro.elib.elib.crwaler.CrawlerService;
import sandro.elib.elib.crwaler.MultiThreadCrawler;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.repository.LibraryRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/crawling")
@Controller
public class CrawlingController {

    private final LibraryRepository libraryRepository;
    private final CrawlerService crawlerService;
    private final MultiThreadCrawler multiThreadCrawler;

    @GetMapping
    public String crawlingPage() {
        return "crawling/index";
    }

    @GetMapping("/thread-test")
    public String threadTest() {
//        List<Library> libraries = libraryRepository.findByTotalBooksGreaterThanAndTotalBooksLessThan(100, 10000);
//        List<Library> libraries = libraryRepository.findAll();
//        libraries.forEach(library -> {
//            Thread thread = new Thread(new MultiThreadCrawler(library));
//            thread.start();
//        });


        List<Library> libraries = libraryRepository.findByTotalBooksGreaterThanAndTotalBooksLessThan(100, 3000);
        libraries.forEach(library -> {
            multiThreadCrawler.setLibrary(library);
            Thread thread = new Thread(multiThreadCrawler);
            thread.start();
        });
        return "crawling/index";
    }

}
