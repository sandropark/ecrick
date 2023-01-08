package com.elib.crawler.repository;

import com.elib.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcTemplateBookRepository {
    private final JdbcTemplate template;

    public void saveAll(List<Book> books) {
        template.batchUpdate(
                "INSERT IGNORE INTO book (title, author, publisher, library_id, public_date, cover_url, category, vendor, created_at, modified_at)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, books.get(i).getTitle());
                        ps.setString(2, books.get(i).getAuthor());
                        ps.setString(3, books.get(i).getPublisher());
                        ps.setLong(4, books.get(i).getLibrary().getId());
                        ps.setDate(5, Date.valueOf(books.get(i).getPublicDate()));
                        ps.setString(6, books.get(i).getCoverUrl());
                        ps.setString(7, books.get(i).getCategory());
                        ps.setString(8, books.get(i).getVendor());
                        LocalDateTime now = LocalDateTime.now();
                        ps.setTimestamp(9, Timestamp.valueOf(now));
                        ps.setTimestamp(10, Timestamp.valueOf(now));
                    }

                    @Override
                    public int getBatchSize() {
                        return books.size();
                    }
                }
        );

    }
}
