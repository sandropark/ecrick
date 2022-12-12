package sandro.elib.elib.dto;

import lombok.Data;
import sandro.elib.elib.domain.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookDetailDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String imageUrl;
    private List<LibraryEbookServiceDto> location;

    private BookDetailDto(Long id, String title, String author, String publisher, LocalDate publicDate, String imageUrl, List<LibraryEbookServiceDto> location) {
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
                    .map(LibraryEbookServiceDto::from)
                    .collect(Collectors.toList())
        );
    }

}
