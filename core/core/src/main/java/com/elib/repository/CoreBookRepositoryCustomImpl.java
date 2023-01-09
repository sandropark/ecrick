package com.elib.repository;

import com.elib.domain.CoreBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.elib.domain.QBook.book;
import static com.elib.domain.QCoreBook.coreBook;

public class CoreBookRepositoryCustomImpl implements CoreBookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private CoreBookRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CoreBook> findAllDuplicateDate() {
        // coreBook과 book을 조인한 다음 제목,저자,출판사로 group by 해서 중복이 있는 데이터 모두 가져오기
        List<Tuple> groupedBooks = queryFactory
                .select(book.title, book.author, book.publisher)
                .from(book)
                .groupBy(book.title, book.author, book.publisher)
                .having(book.publicDate.count().gt(1))
                .fetch();

        return queryFactory.selectFrom(coreBook)
                .join(coreBook.book, book).fetchJoin()
                .where(allEq(groupedBooks))
                .fetch();
    }

    private static BooleanBuilder allEq(List<Tuple> groupedBooks) {
        BooleanBuilder builder = new BooleanBuilder();
        for (Tuple groupedBook : groupedBooks) {
            builder.or(
                    book.title.eq(groupedBook.get(book.title))
                            .and(book.author.eq(groupedBook.get(book.author))
                                    .and(book.publisher.eq(groupedBook.get(book.publisher))))
            );
        }
        return builder;
    }

    @Override
    public List<CoreBook> findByTitleAndAuthorAndPublisher(String title, String author, String publisher) {
        return queryFactory.selectFrom(coreBook)
                .join(coreBook.book, book).fetchJoin()
                .where(book.title.eq(title)
                        .and(book.author.eq(author))
                        .and(book.publisher.eq(publisher)))
                .fetch();
    }
}
