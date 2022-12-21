package com.elib.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class EbookService {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ebook_service_id")
    private Long id;
    @Column(name = "ebook_service_name")
    private String name;

    @OneToMany(mappedBy = "ebookService")
    private final List<Relation> relations = new ArrayList<>();

    protected EbookService() {}

    private EbookService(String name) {
        this.name = name;
    }

    public static EbookService of(String name) {
        return new EbookService(name);
    }

}
