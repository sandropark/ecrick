package sandro.elib.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Relation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "relation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

}
