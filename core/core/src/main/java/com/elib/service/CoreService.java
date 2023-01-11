package com.elib.service;

import com.elib.repository.CoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoreService {

    private final CoreRepository coreRepository;

    @Transactional
    public void deleteByLibrary(Long libraryId) {
        coreRepository.deleteByLibraryId(libraryId);
    }
}