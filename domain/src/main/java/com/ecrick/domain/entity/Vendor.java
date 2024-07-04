package com.ecrick.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Vendor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "vendor_name")
    @Enumerated(value = EnumType.STRING)
    private VendorName name;
    private Integer totalBooks;
    @OneToMany(mappedBy = "vendor")
    private List<Library> libraries;

    protected Vendor() {
    }

    public String getNameValue() {
        return name.getValue();
    }

    @Builder
    private Vendor(Long id, VendorName name, Integer totalBooks) {
        this.id = id;
        this.name = name;
        this.totalBooks = totalBooks;
    }

    public boolean isAladin() {
        return name.isAladin();
    }

    public boolean isKyobo() {
        return name.isKyobo();
    }

    public boolean isYes24() {
        return name.isYes24();
    }

    public boolean isBookcube() {
        return name.isBookcube();
    }

    public boolean isOPMS() {
        return name.isOPMS();
    }

    public boolean isSeoulLib() {
        return name.isSeoulLib();
    }

    public boolean isSeoulEdu() {
        return name.isSeoulEdu();
    }
}
