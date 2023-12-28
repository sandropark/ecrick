package com.ecrick.crawler.service;

import com.ecrick.entity.Library;
import com.ecrick.entity.Vendor;
import com.ecrick.repository.LibraryRepository;
import com.ecrick.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final VendorRepository vendorRepository;

    public Page<Library> searchLibrary(Pageable pageable) {
        return libraryRepository.findAll(pageable);
    }

    public Library getLibrary(Long libraryId) {
        return libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));
    }

    @Transactional
    public void libraryUpdate(Library library, Long vendorId) {
        Vendor vendor = null;
        if (vendorId > 0) {
            vendor = vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new EntityNotFoundException("공급사를 찾을 수 없습니다. vendorId = " + vendorId));
        }
        libraryRepository.findById(library.getId())
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + library.getId()))
                .update(library, vendor);
    }

    @Transactional
    public void delete(Long libraryId) {
        libraryRepository.deleteById(libraryId);
    }

    @Transactional
    public void saveLibrary(Library library) {
        Long vendorId = library.getVendor().getId();
        vendorRepository.findById(vendorId)
                    .orElseThrow(() -> new EntityNotFoundException("공급사를 찾을 수 없습니다. vendorId = " + vendorId));
        libraryRepository.save(library);
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

}
