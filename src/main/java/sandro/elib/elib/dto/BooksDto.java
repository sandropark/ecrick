package sandro.elib.elib.dto;

import lombok.Data;
import sandro.elib.elib.domain.Book;

import java.time.LocalDateTime;

@Data
public class BooksDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publicDate;
    private String imageUrl;

    public BooksDto(Book book) {
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        publisher = book.getPublisher();
        publicDate = book.getPublicDate();
        imageUrl = book.getImageUrl();
    }
}
