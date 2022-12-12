package sandro.elib.elib.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.repository.LibraryRepository;
import sandro.elib.elib.repository.RelationRepository;

import static java.lang.Math.max;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AdminService {

    private final LibraryRepository libraryRepository;
    private final RelationRepository relationRepository;

    @Transactional
    public void deleteLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new IllegalArgumentException("도서관 조회 실패 : 도서관 id를 확인하세요." + libraryId));
        relationRepository.deleteByLibrary(library);
        libraryRepository.delete(library);
        log.info("도서관 삭제 완료 : 도서관 명 = {}", library.getName());
    }

    @Transactional
    public void deleteRelations(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new IllegalArgumentException("도서관 조회 실패 : 도서관 id를 확인하세요." + libraryId));
        int count = relationRepository.deleteByLibrary(library);
        library.setSavedBooks(max((library.getSavedBooks() - count), 0));
        log.info("도서관 연관관계 삭제 완료 : 도서관 명 = {}", library.getName());
    }

}
