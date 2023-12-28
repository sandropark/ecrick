package com.ecrick.crawler.infrastructure;

import com.ecrick.crawler.service.port.RowBookRepository;
import com.ecrick.entity.RowBook;
import com.ecrick.repository.JpaRowBookRepository;
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
public class RowBookRepositoryImpl implements RowBookRepository {
    private final JpaRowBookRepository jpaRowBookRepository;
    private final JdbcTemplate template;

    @Override
    public void saveAll(List<RowBook> rowBooks) {
        template.batchUpdate(
                "INSERT IGNORE INTO row_book (title, author, publisher, library_id, public_date, cover_url, category, vendor, created_at, modified_at)" +
                        "VALUES (?,?,?,?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, rowBooks.get(i).getTitle());
                        ps.setString(2, rowBooks.get(i).getAuthor());
                        ps.setString(3, rowBooks.get(i).getPublisher());
                        ps.setLong(4, rowBooks.get(i).getLibrary().getId());
                        ps.setDate(5, Date.valueOf(rowBooks.get(i).getPublicDate()));
                        ps.setString(6, rowBooks.get(i).getCoverUrl());
                        ps.setString(7, rowBooks.get(i).getCategory());
                        ps.setString(8, rowBooks.get(i).getVendor());
                        LocalDateTime now = LocalDateTime.now();
                        ps.setTimestamp(9, Timestamp.valueOf(now));
                        ps.setTimestamp(10, Timestamp.valueOf(now));
                    }

                    @Override
                    public int getBatchSize() {
                        return rowBooks.size();
                    }
                }
        );
    }

    @Override
    public void mapCoreAndBookIfCore_BookIdIsNull() {
        template.execute(
        "UPDATE row_book c SET c.book_id = " +
                "(select b.id from book b" +
                " where c.title = b.title" +
                " AND c.author = b.author" +
                " AND c.publisher = b.publisher)" +
                "where c.book_id is null"
        );
    }

    @Override
    public void deleteByLibraryId(Long libraryId) {
        jpaRowBookRepository.deleteByLibraryId(libraryId);
    }

    @Override
    public Long count() {
        return jpaRowBookRepository.count();
    }
}
