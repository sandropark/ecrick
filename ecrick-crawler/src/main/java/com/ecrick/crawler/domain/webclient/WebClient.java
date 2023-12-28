package com.ecrick.crawler.domain.webclient;

import com.ecrick.crawler.domain.parser.CrawlerParser;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebClient {

    private final List<CrawlerParser> parsers;

    @Getter
    @Builder
    static class HealthCheckResult {
        private List<Success> successes;
        private List<Failure> failures;

        public void printResult() {
            System.out.println("successCount = " + successes.size());
            System.out.println("failureCount = " + failures.size());
            System.out.println();

            System.out.println("=== Successes ===");
            successes.forEach(Success::print);
            System.out.println();

            System.out.println("=== Failures ===");
            failures.forEach(Failure::print);
        }

        @Getter
        @Builder
        static class Success {
            private Library library;
            private JsoupResponse response;
            private Integer totalBooks;
            private List<RowBook> rowBooks;

            public void print() {
                RowBook rowBook = rowBooks.get(0);
                String body = response.body();
                System.out.println(
                        "[" + library.getName() + "]=" +
                        " response.totalBooks=" + totalBooks +
                        " rowBook=" + rowBook + "\n" +
                        " response=" + body.substring(0, body.length() / 10)
                );
                System.out.println();
            }
        }

        @ToString
        @Getter
        @Builder
        static class Failure {
            private Library library;
            private String exMessage;

            public void print() {
                System.out.println(
                        "[" + library.getName() + "]=" +
                        " emMessage=" + exMessage
                );
                System.out.println();
            }
        }
    }

    public HealthCheckResult healthCheck(List<Library> libraries) {
        List<HealthCheckResult.Success> successes = new ArrayList<>();
        List<HealthCheckResult.Failure> failures = new ArrayList<>();

        List<CompletableFuture<Void>> completableFutures = libraries.stream().map(library ->
                CompletableFuture.runAsync(() -> {
                    CrawlerParser parser = getParser(library);
                    String url = library.getUrl();

                    log.info("[REQUEST] {} {}", library.getName(), url);

                    JsoupResponse jsoupResponse = exchange(url);
                    LibraryResponse libraryResponse = parser.parse(jsoupResponse);
                    List<RowBook> rowBooks = libraryResponse.toRowBooks(library);
                    if (rowBooks.isEmpty()) throw new RuntimeException("rowBooks is empty");
                    Integer totalBooks = libraryResponse.getTotalBooks();
                    if (totalBooks == null || totalBooks.equals(0)) throw new RuntimeException("totalBooks is empty");
                    successes.add(HealthCheckResult.Success.builder().library(library).response(jsoupResponse).rowBooks(rowBooks).totalBooks(totalBooks).build());
                }).exceptionally(throwable -> {
                    log.error("[FAILED] {} {}", library.getName(), library.getUrl(), throwable);
                    failures.add(HealthCheckResult.Failure.builder().library(library).exMessage(throwable.getMessage()).build());
                    return null;
                })
        ).toList();

        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new))
                .thenApply(Void -> completableFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        return HealthCheckResult.builder().successes(successes).failures(failures).build();
    }

    public CrawlResult execute(Library library, int corePoolSize) {
        Executor executor = getExecutor(corePoolSize);
        CrawlerParser parser = getParser(library);

        List<String> detailUrls = library.getDetailUrls();
        List<RequestFailure> failures = new ArrayList<>();
        List<RowBook> rowBooks = new ArrayList<>();

        List<CompletableFuture<Void>> completableFutures = detailUrls.stream()
                .map(detailUrl -> CompletableFuture
                        .runAsync(() -> {
                            log.info("[REQUEST] {} {}", library.getName(), detailUrl);
                            JsoupResponse response = exchange(detailUrl);
                            LibraryResponse libraryResponse = parser.parse(response);
                            rowBooks.addAll(libraryResponse.toRowBooks(library));
                        }, executor)
                        .exceptionally(throwable -> {
                            log.error("[FAILED] {} {}", library.getName(), detailUrl, throwable);
                            failures.add(RequestFailure.builder().url(detailUrl).exMessage(throwable.getMessage()).build());
                            return null;
                        })).toList();

        CompletableFuture.allOf(completableFutures.toArray(CompletableFuture[]::new))
                .thenApply(Void -> completableFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        return CrawlResult.builder()
                .requestUrls(detailUrls)
                .rowBooks(rowBooks)
                .failures(failures)
                .build();
    }

    public JsoupResponse exchange(String url) {
        final String userAgent = "User-Agent";
        final String userAgentValue =
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.5249.207 Whale/3.17.145.18 Safari/537.36";
        try {
            return JsoupResponse.of(Jsoup.connect(url).header(userAgent, userAgentValue).ignoreContentType(true).execute());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CrawlerParser getParser(Library library) {
        return parsers.stream()
                .filter(crawlerParser -> crawlerParser.supports(library))
                .findAny()
                .orElseThrow();
    }

    private Executor getExecutor(int corePoolSize) {
        final String CUSTOM_THREAD_NAME_PREFIX = "CUSTOM_THREAD-";

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setThreadNamePrefix(CUSTOM_THREAD_NAME_PREFIX);
        taskExecutor.initialize();
        return taskExecutor;
    }

}
