package com.ecrick.crawler.domain;

import com.ecrick.domain.repository.BookRepository;
import com.ecrick.domain.repository.CoreRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CrawlerTestSupport {

    @Autowired
    protected CoreRepository coreRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected EntityManager em;
}
