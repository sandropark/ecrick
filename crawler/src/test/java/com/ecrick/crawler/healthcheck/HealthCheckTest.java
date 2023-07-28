package com.ecrick.crawler.healthcheck;

import com.ecrick.domain.Library;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class HealthCheckTest extends HealthCheckTestSupport {

    @Test
    void healthCheck() throws Exception {
        List<Library> libraries = libraryRepository.findAll();

        List<CompletableFuture<Boolean>> libraryFutures = libraries.stream()
                .map(lib -> CompletableFuture.supplyAsync(() -> crawlerClient.exchange(lib), executor))
                .collect(Collectors.toList());

        List<Boolean> result = CompletableFuture.allOf(libraryFutures.toArray(new CompletableFuture[0]))
                .thenApply(Void -> libraryFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();

        assertThat(result.size()).isEqualTo(libraries.size());
        assertThat(result).containsOnly(true);
    }

    @Disabled
    @Test
    void singleTest() throws Exception {
        Library library = libraryRepository.findById(5203L).orElseThrow();

        assertThat(crawlerClient.exchange(library)).isTrue();
    }

}
