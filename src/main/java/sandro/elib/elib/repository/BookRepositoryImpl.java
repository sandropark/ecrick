package sandro.elib.elib.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.dto.BookListDto;
import sandro.elib.elib.dto.QBookListDto;
import sandro.elib.elib.web.dto.BookSearch;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static sandro.elib.elib.domain.QBook.book;

public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public BookRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Override
    public Page<BookListDto> searchPage(BookSearch bookSearch, Pageable pageable) {
        List<BookListDto> content = queryFactory
                .select(new QBookListDto(book))
                .from(book)
                .where(bookContains(bookSearch.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookSort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(book.count())
                .from(book)
                .where(bookContains(bookSearch.getKeyword()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Book findByDto(BookDto bookDto) {
        try {
            return em.createQuery("select b from Book b where b.title = :title and b.author = :author and b.publisher = :publisher", Book.class)
                    .setParameter("title", bookDto.getTitle())
                    .setParameter("author", bookDto.getAuthor())
                    .setParameter("publisher", bookDto.getPublisher())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private OrderSpecifier<?> bookSort(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "title":
                        return new OrderSpecifier<>(direction, book.title);
                    case "author":
                        return new OrderSpecifier<>(direction, book.author);
                    case "publisher":
                        return new OrderSpecifier<>(direction, book.publisher);
                    case "publicDate":
                        return new OrderSpecifier<>(direction, book.publicDate);
                }
            }
        }
        return new OrderSpecifier<>(Order.DESC, book.publicDate);
    }

    private BooleanExpression bookContains(String keyword) {
        return hasText(keyword)
                ? book.title.contains(keyword)
                .or(book.author.contains(keyword))
                .or(book.publisher.contains(keyword))
                : null;
    }
}
