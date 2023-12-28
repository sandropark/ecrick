package com.ecrick.dto;

import com.ecrick.entity.RowBook;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDto {
    private String libraryName;
    private String vendorName;

    public static LocationDto from(RowBook rowBook) {
        return new LocationDto(rowBook.getLibraryName(), rowBook.getVendorName());
    }

    public static LocationDto empty() {
        return new LocationDto(null, null);
    }
}
