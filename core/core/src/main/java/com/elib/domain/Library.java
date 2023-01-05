package com.elib.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Library extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "library_id") private Long id;
    @Column(name = "library_name", nullable = false) private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
    private Integer size;

    protected Library() {}

    public List<String> getDetailUrls() {
        ArrayList<String> detailUrls = new ArrayList<>();
        int maxPage = (totalBooks / size) + 2;

        if (param.contains("&")) {
            String[] params = param.split("&");
            for (int page = 1; page < maxPage; page++) {
                detailUrls.add(url + params[0] + page + "&"+ params[1] + size);
            }
        } else {
            for (int page = 1; page < maxPage; page++) {
                detailUrls.add(url + param + page);
            }
        }

        return detailUrls;
    }

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

    public Boolean isKyoboXml() {
        return vendor.isKyobo() && contentType.isXml();
    }

    public Boolean isKyoboJson() {
        return vendor.isKyobo() && contentType.isJson();
    }

    public Boolean isYes24Xml() {
        return vendor.isYes24() && contentType.isXml();
    }

    public Boolean isYes24Json() {
        return vendor.isYes24() && contentType.isJson();
    }

    public Boolean isBookcube() {
        return vendor.isBookcube() && contentType.isJson();
    }

    public Boolean isOPMS() {
        return vendor.isOPMS() && contentType.isJson();
    }

    public Boolean isAladin() {
        return vendor.isAladin() && contentType.isXml();
    }

    public Boolean isSeoulLib() {
        return vendor.isSeoulLib();
    }

    public Boolean isSeoulEdu() {
        return vendor.isSeoulEdu();
    }

    public void updateTotalBooks(Integer totalBooks) {
        this.totalBooks = totalBooks;
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
