package com.elib.repository;

import com.elib.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    @Override
    @EntityGraph(attributePaths = {"relations"})
    Optional<Book> findById(Long bookId);

    @Modifying
    @Query("delete from Book b where b.library.id = :libraryId")
    void deleteByLibraryId(@Param("libraryId") Long libraryId);
}
