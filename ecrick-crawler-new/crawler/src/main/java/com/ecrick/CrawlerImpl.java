package com.ecrick;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import com.ecrick.parser.CrawlerParser;
import com.ecrick.service.Crawler;
import com.ecrick.service.CrawlerModel;
import com.ecrick.webclient.WebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class CrawlerImpl implements Crawler {

    private final List<CrawlerParser> parsers;

    /**
     * @param library
     * @return CrawlerDomain
     */
    @Override
    public CrawlerModel healthCheck(Library library) {
        log.info("library: {}", library.getName());
        String body = WebClient.execute(library.getUrl());
        return getParser(library).parse(body);
    }

    @Override
    public List<RowBook> crawl(Library library) {
        log.info("library: {}", library.getName());
        CrawlerParser parser = getParser(library);
        List<CompletableFuture<List<RowBook>>> futures = library.getDetailUrls().stream()
                .map(url -> CompletableFuture.supplyAsync(() -> {
                            String body = WebClient.execute(url);
                            return parser.parse(body).getRowBooks();
                        })
                ).toList();

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                .thenApply(Void -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .toList())
                .join();
    }

    private CrawlerParser getParser(Library library) {
        CrawlerParser foundParser = parsers.stream().filter(parser -> parser.supports(library)).findAny().orElseThrow();
        foundParser.setLibrary(library);
        return foundParser;
    }

}
