package sandro.elib.elib.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sandro.elib.elib.dto.LibraryServiceDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publicDate;
    private String imageUrl;
    @OneToMany(mappedBy = "book")
    private final List<BookLibraryServiceMap> bookLibraryServices = new ArrayList<>();

    public Book(String title, String author, String publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public Book(String title, String author, String publisher, LocalDateTime publicDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicDate = publicDate;
    }

    public Book(String title, String author, String publisher, String imageUrl) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
    }

    public List<LibraryServiceDto> createLocation() {
        return bookLibraryServices.stream()
                .map(bls -> new LibraryServiceDto(bls.getLibrary().getName(), bls.getService().getName()))
                .collect(Collectors.toList());
    }
}
