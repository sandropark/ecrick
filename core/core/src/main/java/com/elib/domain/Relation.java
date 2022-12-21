package com.elib.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Relation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "relation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ebook_service_id")
    private EbookService ebookService;

    protected Relation() {}

    public static Relation of(Book book, Library library, EbookService ebookService) {
        return new Relation(null, book, library, ebookService);
    }

}
