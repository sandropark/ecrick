package sandro.elib.elib.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publicDate;
    private String imageUrl;
    @OneToMany(mappedBy = "book")
    private final List<Relation> relations = new ArrayList<>();

}
