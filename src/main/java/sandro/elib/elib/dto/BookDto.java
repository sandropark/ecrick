package sandro.elib.elib.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import sandro.elib.elib.domain.Book;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookDto {

    private Long id;

    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publicDate;
    private String imageUrl;
    private List<LibraryServiceDto> location;

    @QueryProjection
    public BookDto(Book book) {
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        publisher = book.getPublisher();
        publicDate = book.getPublicDate();
        imageUrl = book.getImageUrl();
        location = book.createLocation();
    }
}
