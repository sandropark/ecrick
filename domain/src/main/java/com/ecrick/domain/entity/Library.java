package com.ecrick.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Library extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "library_name", nullable = false)
    private String name;
    private String url;
    private String param;
    private int totalBooks;
    private int savedBooks;
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    @ManyToOne
    @JoinColumn(name = "vendor_id", foreignKey = @ForeignKey(name = "fk_vendor_id"))
    private Vendor vendor;
    private Integer size;

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

    public boolean isKyoboXml() {
        return vendor.isKyobo() && contentType.isXml();
    }

    public boolean isKyoboJson() {
        return vendor.isKyobo() && contentType.isJson();
    }

    public boolean isYes24Xml() {
        return vendor.isYes24() && contentType.isXml();
    }

    public boolean isYes24Json() {
        return vendor.isYes24() && contentType.isJson();
    }

    public boolean isBookcube() {
        return vendor.isBookcube() && contentType.isJson();
    }

    public boolean isOPMS() {
        return vendor.isOPMS() && contentType.isJson();
    }

    public boolean isAladin() {
        return vendor.isAladin() && contentType.isXml();
    }

    public boolean isSeoulLib() {
        return vendor.isSeoulLib();
    }

    public boolean isSeoulEdu() {
        return vendor.isSeoulEdu();
    }

}
