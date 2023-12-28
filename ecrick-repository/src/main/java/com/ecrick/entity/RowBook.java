package com.ecrick.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_title_author_publisher_publicDate_libraryId_vendor",
                columnNames = {"title", "author", "publisher", "publicDate", "library_id", "vendor"})
})
@Entity(name = "row_book")
public class RowBook extends AuditField {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String publisher;
    @Column(nullable = false)
    private LocalDate publicDate;
    private String coverUrl;
    @Column(nullable = false)
    private String vendor;
    private String category;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id", foreignKey = @ForeignKey(name = "fk_library_id"))
    private Library library;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_book_id"))
    private Book book;

    @Builder
    private RowBook(Long id, String title, String author, String publisher, LocalDate publicDate,
                    String coverUrl, String vendor, String category, Library library, Book book) {
        this.id = id;
        this.title = title;
        this.author = ofNullable(author).orElse("");
        this.publisher = ofNullable(publisher).orElse("");
        this.publicDate = ofNullable(publicDate).orElse(LocalDate.of(0, 1, 1));
        this.coverUrl = coverUrl;
        this.vendor = ofNullable(vendor).orElse("");
        this.category = category;
        this.library = library;
        this.book = book;
    }

    public String getVendorName() {
        return library.getVendorName();
    }

    public String getLibraryName() {
        return library.getName();
    }

}