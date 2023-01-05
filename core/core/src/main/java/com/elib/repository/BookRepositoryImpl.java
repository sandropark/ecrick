package com.elib.repository;

import com.elib.domain.Book;
import com.elib.dto.BookListDto;
import com.elib.dto.QBookListDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Supplier;

import static com.elib.domain.QBook.book;
import static org.springframework.util.StringUtils.hasText;

public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private BookRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BookListDto> searchPage(String keyword, Pageable pageable) {
        List<BookListDto> content = queryFactory
                .select(new QBookListDto(book))
                .from(book)
                .where(bookContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookSort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(book.count())
                .from(book)
                .where(bookContains(keyword));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
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

    @Override
    public Boolean notExist(Book entity) {
        Integer fetchOne = queryFactory.selectOne()
                .from(book)
                .where(titleEq(entity)
                        .and(authorEq(entity))
                        .and(publisherEq(entity))
                        .and(publicDateEq(entity))
                        .and(vendorEq(entity))
                        .and(libraryEq(entity)))
                .fetchOne();

        return fetchOne == null;
    }

    private BooleanBuilder vendorEq(Book entity) {
        return nullSafeBuilder(() -> book.vendor.eq(entity.getVendor()));
    }

    private BooleanBuilder titleEq(Book entity) {
        return nullSafeBuilder(() -> book.title.eq(entity.getTitle()));
    }

    private BooleanBuilder authorEq(Book entity) {
        return nullSafeBuilder(() -> book.author.eq(entity.getAuthor()));
    }

    private BooleanBuilder publisherEq(Book entity) {
        return nullSafeBuilder(() -> book.publisher.eq(entity.getPublisher()));
    }

    private BooleanBuilder publicDateEq(Book entity) {
        return nullSafeBuilder(() -> book.publicDate.eq(entity.getPublicDate()));
    }

    private BooleanBuilder libraryEq(Book entity) {
        return nullSafeBuilder(() -> book.library.eq(entity.getLibrary()));
    }

    private static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }

}
