package com.ecrick.repository;

import com.ecrick.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface JpaBookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    @Override
    @EntityGraph(attributePaths = {"rowbooks"})
    Optional<Book> findById(Long bookId);

}
