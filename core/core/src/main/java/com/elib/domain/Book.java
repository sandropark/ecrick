package com.elib.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Book extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;
    private String vendor;
    private String category;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    protected Book() {}

    public Date publicDateToDate() {
        if (publicDate != null) {
            return Date.valueOf(publicDate);
        }
        return null;
    }

    public void update(LocalDate publicDate, String coverUrl) {
        this.publicDate = publicDate;
        this.coverUrl = coverUrl;
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