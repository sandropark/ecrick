package com.ecrick.entity;

import com.ecrick.common.VendorName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@ToString(exclude = "libraries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "vendor")
public class Vendor extends AuditField {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "vendor_name")
    @Enumerated(value = EnumType.STRING)
    private VendorName name;
    private Integer totalBooks;
    @OneToMany(mappedBy = "vendor")
    private List<Library> libraries;

    public String getNameAsString() {
        return name.getValue();
    }

    @Builder
    private Vendor(Long id, VendorName name, Integer totalBooks) {
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
