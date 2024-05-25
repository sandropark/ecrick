package com.ecrick.domain.dto;

import com.ecrick.domain.entity.Core;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDto {
    private String libraryName;
    private String vendorName;

    public static LocationDto from(Core core) {
        return new LocationDto(core.getLibraryName(), core.getVendorName());
    }

    public static LocationDto empty() {
        return new LocationDto(null, null);
    }
}
