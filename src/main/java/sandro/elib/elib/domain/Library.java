package sandro.elib.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Library {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "library_id")
    private Long id;
    @Column(name = "library_name")
    private String name;
    @OneToMany(mappedBy = "library")
    private final List<Relation> relations = new ArrayList<>();

    public Library(String name) {
        this.name = name;
    }
}
