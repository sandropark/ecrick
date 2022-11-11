package sandro.elib.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public static Library of(String name) {
        return new Library(name);
    }
}
