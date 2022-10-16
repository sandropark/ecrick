package sandro.elib.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookLibraryServiceMap {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "book_library_service_map_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    public BookLibraryServiceMap(Book book, Library library, Service service) {
        this.book = book;
        this.library = library;
        this.service = service;
    }
}
