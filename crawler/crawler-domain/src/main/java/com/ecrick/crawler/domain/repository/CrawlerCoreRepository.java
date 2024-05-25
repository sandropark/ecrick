package com.ecrick.crawler.domain.repository;

import com.ecrick.domain.entity.Core;
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
public class CrawlerCoreRepository {
    private final JdbcTemplate template;

    public void saveAll(List<Core> cores) {
        template.batchUpdate(
                "INSERT IGNORE INTO core (title, author, publisher, library_id, public_date, cover_url, category, vendor, created_at, modified_at)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, cores.get(i).getTitle());
                        ps.setString(2, cores.get(i).getAuthor());
                        ps.setString(3, cores.get(i).getPublisher());
                        ps.setLong(4, cores.get(i).getLibrary().getId());
                        ps.setDate(5, Date.valueOf(cores.get(i).getPublicDate()));
                        ps.setString(6, cores.get(i).getCoverUrl());
                        ps.setString(7, cores.get(i).getCategory());
                        ps.setString(8, cores.get(i).getVendor());
                        LocalDateTime now = LocalDateTime.now();
                        ps.setTimestamp(9, Timestamp.valueOf(now));
                        ps.setTimestamp(10, Timestamp.valueOf(now));
                    }

                    @Override
                    public int getBatchSize() {
                        return cores.size();
                    }
                }
        );
    }

    public void mapCoreAndBookIfCore_BookIdIsNull() {
        template.execute(
                "UPDATE core c SET c.book_id = " +
                        "(select b.id from book b" +
                        " where c.title = b.title" +
                        " AND c.author = b.author" +
                        " AND c.publisher = b.publisher)" +
                        "where c.book_id is null"
        );
    }
}
