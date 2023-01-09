package com.elib.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;

    protected Book() {}

    public boolean laterThan(Book book) {
        return publicDate.isAfter(book.publicDate);
    }

    public boolean isSameGroup(Book book) {
        return title.equals(book.title) && author.equals(book.author) && publisher.equals(book.publisher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
