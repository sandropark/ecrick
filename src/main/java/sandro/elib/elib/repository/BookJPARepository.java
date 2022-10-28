package sandro.elib.elib.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.domain.BookSearch;
import sandro.elib.elib.dto.MyPage;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookJPARepository {
    private final EntityManager em;

    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll(BookSearch bookSearch, MyPage myPage) {
        String jpql = getJpql(bookSearch);
        TypedQuery<Book> query = setParamAndGetQuery(bookSearch, jpql);
        return query
                .setFirstResult(myPage.getFirstResult())
                .setMaxResults(myPage.getMaxResult())
                .getResultList();
    }

    private String getJpql(BookSearch bookSearch) {
        StringBuilder sb = new StringBuilder();
        sb.append("select b from Book b");
        if (bookSearch.getTitle() != null) {
            sb.append(" where b.title like concat('%',:title,'%')");
        }
        return sb.toString();
    }

    private TypedQuery<Book> setParamAndGetQuery(BookSearch bookSearch, String jpql) {
        TypedQuery<Book> query = em.createQuery(jpql, Book.class);
        if (bookSearch.getTitle() != null) {
            query.setParameter("title", bookSearch.getTitle());
        }
        return query;
    }
}
