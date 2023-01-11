package com.elib.dto;

import com.elib.domain.Core;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationDto {
    private String library;
    private String vendor;

    public static LocationDto from(Core core) {
        return new LocationDto(core.getLibrary().getName(), core.getLibrary().getVendor().getName().getValue());
    }
}
