package sandro.elib.elib.dto;

import lombok.Data;
import sandro.elib.elib.domain.Book;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookDto {

    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publicDate;
    private String imageUrl;
    private List<LibraryServiceDto> location;

    public BookDto(Book book) {
        title = book.getTitle();
        author = book.getAuthor();
        publisher = book.getPublisher();
        publicDate = book.getPublicDate();
        imageUrl = book.getImageUrl();
        location = book.createLocation();
    }
}
