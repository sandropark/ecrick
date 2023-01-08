package com.elib.repository;

import com.elib.domain.Core;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CoreRepository extends JpaRepository<Core, Long>, CoreRepositoryCustom {
//    @Override
//    @EntityGraph(attributePaths = {"relations"})
//    Optional<Book> findById(Long bookId);

    @Modifying
    @Query("delete from Core c where c.library.id = :libraryId")
    void deleteByLibraryId(@Param("libraryId") Long libraryId);
}