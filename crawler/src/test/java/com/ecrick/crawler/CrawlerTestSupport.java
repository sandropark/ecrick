package com.ecrick.crawler;

import com.ecrick.CrawlerApplication;
import com.ecrick.core.repository.BookRepository;
import com.ecrick.core.repository.CoreRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(
        classes = CrawlerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class CrawlerTestSupport {

    @Autowired
    protected CoreRepository coreRepository;
    @Autowired
    protected BookRepository bookRepository;
    @Autowired
    protected EntityManager em;
}
