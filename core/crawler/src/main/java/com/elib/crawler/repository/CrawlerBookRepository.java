package com.elib.crawler.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CrawlerBookRepository {
    private final JdbcTemplate template;

    public void insertBookFromCore() {
        template.execute(
                "INSERT INTO book (title, author, publisher, public_date, cover_url)" +
                        " select title, author, publisher, max(public_date), max(cover_url) from core" +
                        " group by title, author, publisher" +
                        " ON DUPLICATE KEY UPDATE public_date = VALUES(public_date)"
        );
    }
}
