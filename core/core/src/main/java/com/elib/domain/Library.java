package com.elib.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Library extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "library_id") private Long id;
    @Column(name = "library_name", nullable = false) private String name;
    private String url;
    private String apiUrl;
    private Integer totalBooks;
    private Integer savedBooks;
    private String contentType;
    @OneToMany(mappedBy = "library")
    private final List<Relation> relations = new ArrayList<>();

    protected Library() {}

    public static Library of(String name) {
        return new Library(null, name, null, null, null, null, null);
    }

    public static Library of(String name, String apiUrl) {
        return new Library(null, name, apiUrl, null, null, null, null);
    }

    public static Library of(String name, String url, String apiUrl, Integer totalBooks, Integer savedBooks, String contentType) {
        return new Library(null, name, url, apiUrl, totalBooks, savedBooks, contentType);
    }

    public static Library of(Long id, String name, String url, String apiUrl, Integer totalBooks, Integer savedBooks, String contentType) {
        return new Library(id, name, url, apiUrl, totalBooks, savedBooks, contentType);
    }

    public void update(Library library) {
        url = library.getUrl();
        apiUrl = library.getApiUrl();
        totalBooks = library.getTotalBooks();
        savedBooks = library.getSavedBooks();
        contentType = library.getContentType();
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
