package com.ecrick.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_title_author_publisher_publicDate_libraryId_vendor",
                columnNames = {"title", "author", "publisher", "publicDate", "library_id", "vendor"})
})
@Entity
public class Core extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY) private Long id;
    @Column(nullable = false) private String title;
    @Column(nullable = false) @Builder.Default private String author = "";
    @Column(nullable = false) @Builder.Default private String publisher = "";
    @Column(nullable = false) @Builder.Default private LocalDate publicDate = LocalDate.of(0, 1, 1);
    private String coverUrl;
    @Column(nullable = false) @Builder.Default private String vendor = "";
    private String category;
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "library_id", foreignKey = @ForeignKey(name = "fk_library_id")) private Library library;
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_book_id")) private Book book;

    protected Core() {
    }

    public String getVendorName() {
        return library.getVendorName();
    }

    public String getLibraryName() {
        return library.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Core core = (Core) o;
        return Objects.equals(getId(), core.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}