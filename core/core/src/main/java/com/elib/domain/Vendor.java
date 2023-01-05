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
    @Column(name = "vendor_id")
    private Long id;
    @Column(name = "vendor_name")
    @Enumerated(value = EnumType.STRING)
    private VendorName name;
    private Integer totalBooks;
    @OneToMany(mappedBy = "vendor")
    private List<Library> libraries;

    protected Vendor() {}

    @Builder
    public Vendor(Long id, VendorName name, Integer totalBooks) {
        this.id = id;
        this.name = name;
        this.totalBooks = totalBooks;
    }

    public Boolean isKyobo() {
        return name.isKyobo();
    }

    public Boolean isYes24() {
        return name.isYes24();
    }

    public Boolean isBookcube() {
        return name.isBookcube();
    }

    public Boolean isOPMS() {
        return name.isOPMS();
    }

    public Boolean isAladin() {
        return name.isAladin();
    }

    public Boolean isSeoulLib() {
        return name.isSeoulLib();
    }

    public Boolean isSeoulEdu() {
        return name.isSeoulEdu();
    }
}
