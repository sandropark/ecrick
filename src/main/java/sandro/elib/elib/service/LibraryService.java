package sandro.elib.elib.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.repository.LibraryRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    @Transactional
    public void updateAllSavedBooks() {
        libraryRepository.updateAllSavedBooks();
    }

    @Transactional
    public void updateSavedBooks(Long libraryId) {
        libraryRepository.updateSavedBooks(libraryId);
    }

}
