package com.ecrick.controller.request;

import com.ecrick.common.ContentType;
import com.ecrick.entity.Library;
import com.ecrick.entity.Vendor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryAddRequest {
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private Integer size;

    public Library toModel(Long vendorId) {
        return Library.builder()
                .name(name)
                .url(url)
                .param(param)
                .totalBooks(totalBooks)
                .savedBooks(savedBooks)
                .contentType(contentType)
                .requestSize(size)
                .vendor(Vendor.builder().id(vendorId).build())
                .build();
    }
}
