package com.elib.service;

import com.elib.domain.Vendor;
import com.elib.dto.LibraryDto;
import com.elib.repository.LibraryRepository;
import com.elib.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final VendorRepository vendorRepository;

    public Page<LibraryDto> searchLibrary(Pageable pageable) {
        return libraryRepository.findAll(pageable)
                .map(LibraryDto::from);
    }

    public LibraryDto getLibrary(Long libraryId) {
        return libraryRepository.findById(libraryId)
                .map(LibraryDto::from)
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));
    }

    @Transactional
    public void libraryUpdate(LibraryDto dto, Long vendorId) {
        Vendor vendor = null;
        if (vendorId > 0) {
            vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new EntityNotFoundException("공급사를 찾을 수 없습니다. vendorId = " + vendorId));
        }
        libraryRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + dto.getId()))
                .update(dto.toEntity(), vendor);
    }

    @Transactional
    public void delete(Long libraryId) {
        libraryRepository.deleteById(libraryId);
    }

    @Transactional
    public void saveLibrary(LibraryDto dto, Long vendorId) {
        Vendor vendor = null;
        if (vendorId > 0)
            vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new EntityNotFoundException("공급사를 찾을 수 없습니다. vendorId = " + vendorId));
        libraryRepository.save(dto.toEntity(vendor));
    }

    @Transactional
    public void updateAllSavedBooks() {
        libraryRepository.updateAllSavedBooks();
    }

    @Transactional
    public void updateSavedBooks(Long libraryId) {
        libraryRepository.updateSavedBooks(libraryId);
    }

    @Transactional
    public void updateTotalBooks(Long id, Integer totalBooks) {
        libraryRepository.findById(id)
                .ifPresent(library -> library.updateTotalBooks(totalBooks));
    }

    public List<String> getNames() {
        Pattern compile = Pattern.compile("\\(.+\\)");
        return libraryRepository.findAllNames().stream()
                .map(name -> compile.matcher(name).replaceAll(""))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
