package com.ecrick.entity;

import com.ecrick.common.ContentType;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@ToString
@EqualsAndHashCode(callSuper = false, of = "id")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity(name = "library")
public class Library extends AuditField {

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
    private Integer requestSize;

    public void update(Library library, Vendor vendor) {
        name = library.getName();
        url = library.getUrl();
        param = library.getParam();
        totalBooks = library.getTotalBooks();
        savedBooks = library.getSavedBooks();
        contentType = library.getContentType();
        requestSize = library.getRequestSize();
        this.vendor = vendor;
    }

    public void updateTotalBooks(Integer totalBooks) {
        this.totalBooks = totalBooks;
    }

    public String getVendorName() {
        return vendor.getNameAsString();
    }

    public List<String> getDetailUrls() {
        if (vendor.isAladin()) return getAladinDetailUrls();
        if (param.contains("&")) return getPageAndSizeDetailUrls();
        return getPageDetailUrls();
    }

    private List<String> getPageDetailUrls() {
        List<String> detailUrls = new ArrayList<>();
        int maxPage = totalBooks / requestSize + 2;

        for (int page = 1; page < maxPage; page++)
            detailUrls.add(url + param + page);

        return detailUrls;
    }

    private List<String> getPageAndSizeDetailUrls() {
        List<String> detailUrls = new ArrayList<>();
        int maxPage = totalBooks / requestSize + 2;

        String[] params = param.split("&");
        for (int page = 1; page < maxPage; page++) {
            detailUrls.add(url + params[0] + page + "&"+ params[1] + requestSize);
        }
        return detailUrls;
    }

    private List<String> getAladinDetailUrls() {
        ArrayList<String> detailUrls = new ArrayList<>();
        for (int page = 1; page < totalBooks; page+= requestSize) {
            detailUrls.add(url + "currentPage=" + page);
        }
        detailUrls.add(url + "currentPage=" + totalBooks);
        return detailUrls;
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

    public String getFileName() {
        return name.replace("/", "_") + "_" + vendor.getNameAsString() + contentType.getExtension();
    }
}