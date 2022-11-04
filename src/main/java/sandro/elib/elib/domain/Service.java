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
public class Service {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "service_id")
    private Long id;
    @Column(name = "service_name")
    private String name;

    @OneToMany(mappedBy = "service")
    private final List<Relation> relations = new ArrayList<>();

    public Service(String name) {
        this.name = name;
    }
}
