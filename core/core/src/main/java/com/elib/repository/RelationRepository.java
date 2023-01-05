package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.Vendor;
import com.elib.domain.Library;
import com.elib.domain.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Modifying
    @Query("delete from Relation r where r.library.id = :libraryId")
    void deleteByLibraryId(@Param("libraryId") Long libraryId);

    boolean existsByBookAndLibraryAndVendor(Book book, Library library, Vendor vendor);
}