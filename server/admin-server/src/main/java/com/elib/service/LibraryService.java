package com.elib.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.elib.domain.Library;
import com.elib.dto.LibraryDto;
import com.elib.repository.LibraryRepository;
import com.elib.repository.RelationRepository;
import com.elib.web.dto.LibraryAddFormDto;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final RelationRepository relationRepository;

    public Page<LibraryDto> searchLibrary(Pageable pageable) {
        return libraryRepository.findAll(pageable)
                .map(LibraryDto::from);
    }

    public LibraryDto getLibraryDto(Long libraryId) {
        return libraryRepository.findById(libraryId)
                .map(LibraryDto::from)
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));
    }

    @Transactional
    public void save(LibraryAddFormDto dto) {
        libraryRepository.save(dto.toEntity());
    }

    @Transactional
    public void updateSavedBooks(Long libraryId) {
        libraryRepository.updateSavedBooks(libraryId);
    }

    @Transactional
    public void update(LibraryDto dto) {
        Library library = libraryRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + dto.getId()));
        library.update(dto.toEntity());
    }

    @Transactional
    public void delete(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));
        relationRepository.deleteByLibrary(library);
        libraryRepository.delete(library);
    }
}
