package com.ecrick.crawler;

import com.ecrick.CrawlerApplication;
import com.ecrick.repository.BookRepository;
import com.ecrick.repository.CoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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