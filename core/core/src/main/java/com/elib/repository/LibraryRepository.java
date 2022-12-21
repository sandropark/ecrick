package com.elib.repository;

import com.elib.domain.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibraryRepository extends JpaRepository<Library, Long> {

    @Modifying
    @Query("update Library l set l.savedBooks = (select count(r) from Relation r where l = r.library) where l.id = :id" )
    void updateSavedBooks(@Param("id") Long id);

}