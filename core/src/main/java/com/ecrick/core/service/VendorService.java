package com.ecrick.core.service;

import com.ecrick.core.domain.Vendor;
import com.ecrick.core.dto.VendorDto;
import com.ecrick.core.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VendorService {
    private final VendorRepository vendorRepository;

    public List<VendorDto> findAll() {
        List<VendorDto> vendors = new ArrayList<>();

        List<Vendor> vendorList = vendorRepository.findAll();

        for (Vendor vendor : vendorList) {
            vendors.add(VendorDto.from(vendor));
        }

        return vendors;
    }
}
