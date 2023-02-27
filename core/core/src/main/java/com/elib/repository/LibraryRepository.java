package com.elib.repository;

import com.elib.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    @Modifying
    @Query("update Library l set l.savedBooks = (select count(c) from Core c where l = c.library) where l.id = :id" )
    void updateSavedBooks(@Param("id") Long id);

    @Modifying
    @Query("update Library l set l.savedBooks = (select count(c) from Core c where l = c.library)")
    void updateAllSavedBooks();

    @Query("select distinct l.name from Library l")
    List<String> findAllNames();

}