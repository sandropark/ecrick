package sandro.elib.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sandro.elib.elib.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>,BookRepositoryCustom {
}
