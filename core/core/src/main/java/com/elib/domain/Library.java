package com.elib.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Library extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "library_id") private Long id;
    @Column(name = "library_name", nullable = false) private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private String contentType;
    private String service;
    @OneToMany(mappedBy = "library")
    private final List<Relation> relations = new ArrayList<>();

    protected Library() {}

    @Builder
    private Library(Long id, String name, String url, String param, Integer totalBooks, Integer savedBooks, String contentType, String service) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.param = param;
        this.totalBooks = totalBooks;
        this.savedBooks = savedBooks;
        this.contentType = contentType;
        this.service = service;
    }

    public void update(Library library) {
        url = library.getUrl();
        param = library.getParam();
        totalBooks = library.getTotalBooks();
        savedBooks = library.getSavedBooks();
        contentType = library.getContentType();
        service = library.getService();
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
