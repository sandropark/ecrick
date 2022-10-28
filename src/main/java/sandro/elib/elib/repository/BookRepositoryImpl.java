package sandro.elib.elib.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import sandro.elib.elib.domain.BookSearch;
import sandro.elib.elib.dto.BooksDto;
import sandro.elib.elib.dto.QBooksDto;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static sandro.elib.elib.domain.QBook.book;

public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BooksDto> searchPage(BookSearch bookSearch, Pageable pageable) {
        List<BooksDto> content = queryFactory
                .select(new QBooksDto(book))
                .from(book)
                .where(bookContains(bookSearch.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(book.count())
                .from(book)
                .where(bookContains(bookSearch.getKeyword()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression bookContains(String keyword) {
        return hasText(keyword)
                ? book.title.contains(keyword)
                .or(book.author.contains(keyword))
                .or(book.publisher.contains(keyword))
                : null;
    }
}
