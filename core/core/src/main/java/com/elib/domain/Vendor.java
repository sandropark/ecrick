package com.elib.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Vendor extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "vendor_name")
    @Enumerated(value = EnumType.STRING)
    private VendorName name;
    private Integer totalBooks;
    @OneToMany(mappedBy = "vendor")
    private List<Library> libraries;

    protected Vendor() {}

    @Builder
    private Vendor(Long id, VendorName name, Integer totalBooks) {
        this.id = id;
        this.name = name;
        this.totalBooks = totalBooks;
    }

}
