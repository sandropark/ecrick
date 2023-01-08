package com.elib.service;

import com.elib.dto.CoreDetailDto;
import com.elib.dto.CoreListDto;
import com.elib.repository.CoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoreService {

    private final CoreRepository coreRepository;

    public CoreDetailDto getBookDetail(Long bookId) {
        return coreRepository.findById(bookId)
                .map(CoreDetailDto::from)
                .orElseThrow(() -> new EntityNotFoundException("책을 찾을 수 없습니다 - bookId: " + bookId));
    }

    public Page<CoreListDto> searchPage(String keyword, Pageable pageable) {
        return coreRepository.searchPage(keyword, pageable);
    }

    @Transactional
    public void deleteByLibrary(Long libraryId) {
        coreRepository.deleteByLibraryId(libraryId);
    }
}