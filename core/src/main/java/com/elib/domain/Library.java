package com.elib.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Library extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY) private Long id;
    @Column(name = "library_name", nullable = false) private String name;
    private String url;
    private String param;
    private int totalBooks;
    private int savedBooks;
    @Enumerated(EnumType.STRING) private ContentType contentType;
    @ManyToOne @JoinColumn(name = "vendor_id", foreignKey = @ForeignKey(name = "fk_vendor_id")) private Vendor vendor;
    private Integer size;

    protected Library() {}

    public void update(Library library, Vendor vendor) {
        name = library.getName();
        url = library.getUrl();
        param = library.getParam();
        totalBooks = library.getTotalBooks();
        savedBooks = library.getSavedBooks();
        contentType = library.getContentType();
        size = library.getSize();
        this.vendor = vendor;
    }

    public void updateTotalBooks(Integer totalBooks) {
        this.totalBooks = totalBooks;
    }

    public String getVendorName() {
        return vendor.getNameValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(getId(), library.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
