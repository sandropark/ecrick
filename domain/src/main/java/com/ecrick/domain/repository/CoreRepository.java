package com.ecrick.domain.repository;

import com.ecrick.domain.entity.Core;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoreRepository extends JpaRepository<Core, Long> {

    @Modifying
    @Query("delete from Core c where c.library.id = :libraryId")
    void deleteByLibraryId(@Param("libraryId") Long libraryId);
}
