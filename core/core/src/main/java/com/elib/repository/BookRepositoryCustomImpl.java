package com.elib.repository;

import com.elib.domain.Book;
import com.elib.dto.BookListDto;
import com.elib.dto.QBookListDto;
import com.elib.dto.Search;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.elib.domain.QBook.book;
import static org.springframework.util.StringUtils.hasText;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private BookRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BookListDto> searchPage(Search search, Pageable pageable) {
        List<BookListDto> content = queryFactory
                .select(new QBookListDto(book))
                .from(book)
                .where(bookContains(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookSort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(book.count())
                .from(book)
                .where(bookContains(search));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression bookContains(Search search) {
        String keyword = search.getKeyword();
        if (hasText(keyword)) {
            switch (search.getSearchTarget()) {
                case TOTAL:
                    return book.title.contains(keyword)
                            .or(book.author.contains(keyword))
                            .or(book.publisher.contains(keyword));
                case TITLE:
                    return book.title.contains(keyword);
                case AUTHOR:
                    return book.author.contains(keyword);
                case PUBLISHER:
                    return book.publisher.contains(keyword);
            }
        }
        return null;
    }

    private OrderSpecifier[] bookSort(Pageable pageable) {
        return pageable.getSort().get()
                .map(order -> new OrderSpecifier(getDirection(order), getTarget(order)))
                .toArray(OrderSpecifier[]::new);
    }

    private PathBuilder<?> getTarget(Sort.Order order) {
        return new PathBuilder<>(Book.class, "book").get(order.getProperty());
    }

    private Order getDirection(Sort.Order order) {
        return order.getDirection().isAscending() ? Order.ASC : Order.DESC;
    }

}
