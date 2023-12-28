package com.ecrick.webclient;

import com.ecrick.entity.Library;
import com.ecrick.repository.LibraryRepository;
import com.ecrick.util.FileIOUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBootTest
class WebClientTest {

    @Autowired
    LibraryRepository libraryRepository;

    @Test
    void downloadTestData() throws Exception {
        libraryRepository.findAllByIdIn(toList(5234L, 5235L, 5236L))
                .forEach(WebClientTest::downloadData);
    }

    @Test
    void healthCheck() throws Exception {
        // Given
        List<Library> successes = new ArrayList<>();
        List<Map<String, String>> failures = new ArrayList<>();
        List<Library> libraries = libraryRepository.findAll();

        // When
        List<CompletableFuture<Void>> futures = libraries.stream().map(library ->
                CompletableFuture.runAsync(() -> {
                            downloadData(library);
                            successes.add(library);
                        }, getExecutor(110))
                        .exceptionally(throwable -> {
                            failures.add(Map.of(library.getName() + "_" + library.getVendorName(), throwable.getMessage()));
                            return null;
                        })
        ).toList();
        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        // Then
        System.out.println("failures = " + failures);
        assertThat(successes.size()).isEqualTo(libraries.size());
    }

    private List<Long> toList(Long... longs) {
        return Arrays.asList(longs);
    }

    private static void downloadData(Library library) {
        String body = WebClient.execute(library.getUrl());
        FileIOUtil.write(library.getFileName(), body);
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