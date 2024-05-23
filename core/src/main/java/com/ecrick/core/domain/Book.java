package com.ecrick.core.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString(exclude = {"cores"})
@EqualsAndHashCode(of = {"id"})
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_title_author_publisher", columnNames = {"title", "author", "publisher"})
})
@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;
    private String fullInfo;

    @OneToMany(mappedBy = "book")
    private List<Core> cores;

    protected Book() {
    }

    @Builder
    private Book(String title, String author, String publisher, LocalDate publicDate, String coverUrl, List<Core> cores) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicDate = publicDate;
        this.coverUrl = coverUrl;
        this.cores = cores;
    }

}
