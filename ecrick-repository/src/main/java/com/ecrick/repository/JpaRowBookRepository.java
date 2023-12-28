package com.ecrick.repository;

import com.ecrick.entity.RowBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaRowBookRepository extends JpaRepository<RowBook, Long> {

    @Modifying
    @Query("delete from row_book rb where rb.library.id = :libraryId")
    void deleteByLibraryId(@Param("libraryId") Long libraryId);
}
