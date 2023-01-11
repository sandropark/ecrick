package com.elib.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;
    @OneToMany(mappedBy = "book")
    private List<Core> cores;

    protected Book() {}

    public static Book fromCore(Core core) {
        return Book.builder()
                .title(core.getTitle())
                .author(core.getAuthor())
                .publisher(core.getPublisher())
                .publicDate(core.getPublicDate())
                .coverUrl(core.getCoverUrl())
                .build();
    }

    public void updateDate(LocalDate publicDate) {
        this.publicDate = publicDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
