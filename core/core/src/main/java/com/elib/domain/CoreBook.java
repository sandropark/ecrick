package com.elib.domain;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class CoreBook {
    @Id private Long coreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @Getter private Book book;

    protected CoreBook() {}

    public boolean laterThan(CoreBook coreBook) {
        return book.laterThan(coreBook.book);
    }

    public boolean isSameGroup(CoreBook coreBook) {
        return book.isSameGroup(coreBook.book);
    }

    public Book updateBook(CoreBook latestDateBook) {
        Book oldBook = book;
        book = latestDateBook.book;
        return oldBook;
    }
}
