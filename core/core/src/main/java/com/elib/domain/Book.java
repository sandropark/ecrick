package com.elib.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String imageUrl;
    @OneToMany(mappedBy = "book")
    private final List<Relation> relations = new ArrayList<>();

    protected Book() {}

    public static Book of(String title, String author, String publisher, LocalDate publicDate, String imageUrl) {
        return new Book(null, title, author, publisher, publicDate, imageUrl);
    }

    public static Book of(String title) {
        return new Book(null, title, null, null, null, null);
    }


    public void updatePublicDate(LocalDate publicDate) {
        this.publicDate = publicDate;
    }

    public boolean hasNotPublicDate() {
        return publicDate == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getPublisher(), book.getPublisher());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor(), getPublisher());
    }
}
