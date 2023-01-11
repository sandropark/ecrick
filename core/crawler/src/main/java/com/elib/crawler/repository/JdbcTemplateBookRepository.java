package com.elib.crawler.repository;

import com.elib.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcTemplateBookRepository {
    private final JdbcTemplate template;

    public void saveAll(List<Book> books) {
        template.batchUpdate("insert into book (title, author, publisher, public_date)" +
                        "values (?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, books.get(i).getTitle());
                        ps.setString(2, books.get(i).getAuthor());
                        ps.setString(3, books.get(i).getPublisher());
                        ps.setDate(4, Date.valueOf(books.get(i).getPublicDate()));
                    }

                    @Override
                    public int getBatchSize() {
                        return books.size();
                    }
                }
        );
    }

    public void insertFromCore() {
        template.execute(
                "insert into book (title, author, publisher, public_date, cover_url)" +
                        " select title, author, publisher, max(public_date), min(cover_url) from core" +
                        " group by title, author, publisher"
        );
    }
}
