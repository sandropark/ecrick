package sandro.elib.elib.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sandro.elib.elib.domain.Book;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager em;

    public List<Book> findAll() {
        String jpql = "select b from Book b";
        return em.createQuery(jpql, Book.class)
                .getResultList();
    }
}
