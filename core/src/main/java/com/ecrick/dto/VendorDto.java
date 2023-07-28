package com.ecrick.dto;

import com.ecrick.domain.Vendor;
import com.ecrick.domain.VendorName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VendorDto {
    private Long id;
    private VendorName name;
    private Integer totalBooks;

    @QueryProjection
    public VendorDto(Long id, VendorName name, Integer totalBooks) {
        this.id = id;
        this.name = name;
        this.totalBooks = totalBooks;
    }

    public String getName() {
        return name.getValue();
    }

    public static VendorDto from(Vendor entity) {
        if (entity != null) {
            return VendorDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .totalBooks(entity.getTotalBooks())
                    .build();
        }
        return VendorDto.builder().build();
    }

    public Vendor toEntity() {
        return Vendor.builder()
                .id(id)
                .name(name)
                .totalBooks(totalBooks)
                .build();
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
