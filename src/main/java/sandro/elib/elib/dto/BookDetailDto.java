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
    private List<LibraryEbookServiceDto> location;

    @QueryProjection
    private BookDetailDto(Long id, String title, String author, String publisher, LocalDateTime publicDate, String imageUrl, List<LibraryEbookServiceDto> location) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicDate = publicDate;
        this.imageUrl = imageUrl;
        this.location = location;
    }

    public static BookDetailDto from(Book entity) {
        return new BookDetailDto(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthor(),
            entity.getPublisher(),
            entity.getPublicDate(),
            entity.getImageUrl(),
            entity.getRelations().stream()
                    .map(relation -> new LibraryEbookServiceDto(relation.getLibrary().getName(), relation.getEbookService().getName()))
                    .collect(Collectors.toList())
        );
    }

}
