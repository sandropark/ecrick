package com.ecrick.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@ToString(exclude = {"rowBooks"})
@EqualsAndHashCode(callSuper = false, of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_title_author_publisher", columnNames = {"title", "author", "publisher"})
})
@Entity
public class Book extends AuditField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;
    private String fullInfo;
    @OneToMany(mappedBy = "book")
    private List<RowBook> rowBooks;

    @Builder
    private Book(String title, String author, String publisher, LocalDate publicDate, String coverUrl, List<RowBook> rowBooks) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicDate = publicDate;
        this.coverUrl = coverUrl;
        this.rowBooks = rowBooks;
    }

}
