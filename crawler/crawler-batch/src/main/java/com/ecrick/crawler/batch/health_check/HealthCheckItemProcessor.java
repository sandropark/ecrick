package com.ecrick.crawler.batch.health_check;

import com.ecrick.crawler.domain.CrawlerClient;
import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.domain.exception.CrawlerException;
import com.ecrick.domain.entity.Library;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class HealthCheckItemProcessor implements ItemProcessor<Library, ResponseDto> {
    private final CrawlerClient crawlerClient;
    private final FailedLibraryList failedLibraryList;

    @Override
    public ResponseDto process(Library library) throws Exception {
        try {
            return crawlerClient.request(library);
        } catch (CrawlerException e) {
            failedLibraryList.add(FailedLibraryList.FailedLibrary.builder()
                    .library(library)
                    .e(e)
                    .build());
            throw e;
        }
    }
}
