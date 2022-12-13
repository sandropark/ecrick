package sandro.elib.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Library extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "library_id") private Long id;
    @Column(name = "library_name") private String name;
    private String url;
    private String apiUrl;
    @Setter private String referrer;
    private Integer totalBooks;
    @Setter private Integer savedBooks;
    private String contentType;
    @OneToMany(mappedBy = "library")
    private final List<Relation> relations = new ArrayList<>();

    private Library(String name) {
        this.name = name;
    }

    private Library(String name, String apiUrl, String referrer) {
        this.name = name;
        this.apiUrl = apiUrl;
        this.referrer = referrer;
    }

    public static Library of(String name) {
        return new Library(name);
    }

    public static Library of(String name, String apiUrl, String referrer) {
        return new Library(name, apiUrl, referrer);
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
