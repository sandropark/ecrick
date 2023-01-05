package com.elib.service;

import com.elib.domain.Vendor;
import com.elib.dto.VendorDto;
import com.elib.repository.VendorRepository;
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
