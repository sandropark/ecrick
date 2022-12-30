package com.elib.service;

import com.elib.dto.LibraryDto;
import com.elib.repository.LibraryRepository;
import com.elib.repository.RelationRepository;
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
    public void save(LibraryDto dto) {
        libraryRepository.save(dto.toEntity());
    }

    @Transactional
    public void update(LibraryDto dto) {
        libraryRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + dto.getId()))
                .update(dto.toEntity());
    }

    @Transactional
    public void delete(Long libraryId) {
        relationRepository.deleteByLibraryId(libraryId);
        libraryRepository.deleteById(libraryId);
    }

}
