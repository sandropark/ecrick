package com.ecrick.domain.repository;

import com.ecrick.domain.entity.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    @Modifying
    @Query("update Library l set l.savedBooks = (select count(c) from Core c where l = c.library) where l.id = :id")
    void updateSavedBooks(@Param("id") Long id);

    @Modifying
    @Query("update Library l set l.savedBooks = (select count(c) from Core c where l = c.library)")
    void updateAllSavedBooks();

    @Query("select distinct l.name from Library l")
    List<String> findAllNames();

    @EntityGraph(attributePaths = {"vendor"})
    @Override
    List<Library> findAll();

    @EntityGraph(attributePaths = {"vendor"})
    @Override
    Page<Library> findAll(Pageable pageable);
}