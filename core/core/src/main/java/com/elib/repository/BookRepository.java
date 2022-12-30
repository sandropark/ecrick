package com.elib.repository;

import com.elib.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    @Override
    @EntityGraph(attributePaths = {"relations"})
    Optional<Book> findById(Long bookId);

    Optional<Book> findByTitleAndAuthorAndPublisher(String title, String author, String publisher);
}
