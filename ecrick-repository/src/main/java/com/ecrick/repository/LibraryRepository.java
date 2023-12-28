package com.ecrick.repository;

import com.ecrick.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    @Modifying
    @Query("update library l set l.savedBooks = (select count(rb) from row_book rb where l = rb.library) where l.id = :id" )
    void updateSavedBooks(@Param("id") Long id);

    @Modifying
    @Query("update library l set l.savedBooks = (select count(rb) from row_book rb where l = rb.library)")
    void updateAllSavedBooks();

    @Query("select distinct l.name from library l")
    List<String> findAllNames();

    List<Library> findAllByIdIn(Collection<Long> id);
}