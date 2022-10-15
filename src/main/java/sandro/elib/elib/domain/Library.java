package sandro.elib.elib.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Library {

    @Id @GeneratedValue
    @Column(name = "library_id")
    private Long id;
    private String name;

    public Library(String name) {
        this.name = name;
    }
}
