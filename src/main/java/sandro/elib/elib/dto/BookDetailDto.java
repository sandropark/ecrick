package sandro.elib.elib.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import sandro.elib.elib.domain.Book;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookDetailDto {

    private Long id;

    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publicDate;
    private String imageUrl;
    private List<LibraryServiceDto> location;

    @QueryProjection
    public BookDetailDto(Book entity) {
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor();
        publisher = entity.getPublisher();
        publicDate = entity.getPublicDate();
        imageUrl = entity.getImageUrl();
        location = entity.getRelations().stream()
                .map(relation -> new LibraryServiceDto(relation.getLibrary().getName(), relation.getService().getName()))
                .collect(Collectors.toList());
    }

}
