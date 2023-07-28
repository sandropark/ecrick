package com.ecrick.crawler;

import com.ecrick.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RepoTest {

    @Autowired
    LibraryRepository libraryRepository;

    @Test
    void test() throws Exception {
        int size = libraryRepository.findAll().size();
        System.out.println("size = " + size);
    }

    @SpringBootApplication(scanBasePackages = "com.ecrick.core.repository")
    static class Context {}
}
