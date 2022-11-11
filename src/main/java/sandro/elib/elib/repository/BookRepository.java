package sandro.elib.elib.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sandro.elib.elib.domain.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>,BookRepositoryCustom {
    @Override
    @EntityGraph(attributePaths = {"relations"})
    Optional<Book> findById(Long aLong);
}
