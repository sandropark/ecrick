package sandro.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private EbookService(String name) {
        this.name = name;
    }

    public static EbookService of(String name) {
        return new EbookService(name);
    }

}
