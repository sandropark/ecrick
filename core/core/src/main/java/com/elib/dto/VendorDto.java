package com.elib.dto;

import com.elib.domain.Vendor;
import com.elib.domain.VendorName;
import lombok.*;

@Getter @Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class VendorDto {
    private Long id;
    private VendorName name;
    private Integer totalBooks;

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

}
