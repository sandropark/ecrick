package com.ecrick.crawler.domain.webclient;

import com.ecrick.entity.RowBook;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CrawlResult {
    private final List<String> requestUrls;
    private final List<RowBook> rowBooks;
    private final List<RequestFailure> failures;
    private final int successCount;
    private final int failureCount;

    @Builder
    public CrawlResult(List<String> requestUrls, List<RowBook> rowBooks, List<RequestFailure> failures) {
        this.requestUrls = requestUrls;
        this.rowBooks = rowBooks;
        this.failures = failures;
        this.failureCount = failures.size();
        this.successCount = requestUrls.size() - failureCount;
    }

    public void printResult() {
        System.out.println("SuccessCount() = " + successCount);
        System.out.println("FailureCount() = " + failureCount);
        System.out.println("totalBooks = " + rowBooks.size());
        failures.forEach(RequestFailure::print);
    }
}
