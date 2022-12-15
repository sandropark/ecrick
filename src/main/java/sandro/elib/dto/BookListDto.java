package sandro.elib.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import sandro.elib.domain.Book;

import java.time.LocalDate;

@Data
public class BookListDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String imageUrl;

    @QueryProjection
    public BookListDto(Book book) {
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
        publisher = book.getPublisher();
        publicDate = book.getPublicDate();
        imageUrl = book.getImageUrl();
    }

}
