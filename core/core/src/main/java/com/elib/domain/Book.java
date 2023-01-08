package com.elib.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author", "publisher", "publicDate", "library_id", "vendor"})
})
@Entity
public class Book extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    @Builder.Default
    private String author = "";
    @Column(nullable = false)
    @Builder.Default
    private String publisher = "";
    @Column(nullable = false)
    @Builder.Default
    private LocalDate publicDate = LocalDate.of(0, 1, 1);
    private String coverUrl;
    @Column(nullable = false)
    @Builder.Default
    private String vendor = "";
    private String category;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    protected Book() {}

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