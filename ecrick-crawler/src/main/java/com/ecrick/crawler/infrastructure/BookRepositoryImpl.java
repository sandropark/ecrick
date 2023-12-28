package com.ecrick.crawler.infrastructure;

import com.ecrick.crawler.service.port.BookRepository;
import com.ecrick.repository.JpaBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final JpaBookRepository jpaBookRepository;
    private final JdbcTemplate template;

    @Override
    public void insertBookFromCore() {
        template.execute(
                "INSERT INTO book (title, author, publisher, public_date, cover_url)" +
                        " select title, author, publisher, max(public_date), max(cover_url) from row_book" +
                        " group by title, author, publisher" +
                        " ON DUPLICATE KEY UPDATE public_date = VALUES(public_date)"
        );
    }

    @Override
    public Long count() {
        return jpaBookRepository.count();
    }
}
