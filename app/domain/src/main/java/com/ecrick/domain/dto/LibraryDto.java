package com.ecrick.domain.dto;

import com.ecrick.domain.entity.ContentType;
import com.ecrick.domain.entity.Library;
import com.ecrick.domain.entity.Vendor;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class LibraryDto {

    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private VendorDto vendor;
    private Integer size;

    public static LibraryDto from(Library entity) {
        return LibraryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .url(entity.getUrl())
                .param(entity.getParam())
                .totalBooks(entity.getTotalBooks())
                .savedBooks(entity.getSavedBooks())
                .contentType(entity.getContentType())
                .vendor(VendorDto.from(entity.getVendor()))
                .size(entity.getSize())
                .build();
    }

    public Library toEntity() {
        return Library.builder()
                .id(id)
                .name(name)
                .url(url)
                .param(param)
                .totalBooks(totalBooks)
                .savedBooks(savedBooks)
                .contentType(contentType)
                .size(size)
                .build();
    }

    public Library toEntity(Vendor vendor) {
        return Library.builder()
                .name(name)
                .url(url)
                .param(param)
                .totalBooks(totalBooks)
                .savedBooks(savedBooks)
                .contentType(contentType)
                .vendor(vendor)
                .size(size)
                .build();
    }

}
