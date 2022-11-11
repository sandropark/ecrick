package sandro.elib.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"book_id", "library_id", "ebook_service_id"}
        )
})
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

    private Relation(Book book, Library library, EbookService ebookService) {
        this.book = book;
        this.library = library;
        this.ebookService = ebookService;
    }

    public static Relation of(Book book, Library library, EbookService ebookService) {
        return new Relation(book, library, ebookService);
    }

}
