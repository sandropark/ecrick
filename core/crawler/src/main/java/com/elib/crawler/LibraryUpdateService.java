package com.elib.crawler;

import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;
import com.elib.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static com.elib.crawler.CrawlerUtil.getResponseDto;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LibraryUpdateService implements Runnable {

    private final LibraryRepository libraryRepository;
    private Long libraryId;
    private String option;

    public void init(Long libraryId, String option) {
        this.libraryId = libraryId;
        this.option = option;
    }

    @Override
    public void run() {
        if (option.equals("total")) {
            updateTotalBooks();
        }

        if (option.equals("saved")) {
            updateSavedBooks();
        }
    }

    @Transactional
    private void updateSavedBooks() {
        log.info("저장된 도서 업데이트 library_id = {}", libraryId);
        libraryRepository.updateSavedBooks(libraryId);
    }

    private void updateTotalBooks() {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));
        log.info("{} 총 도서수 업데이트", library.getName());

        getResponseDto(library.getUrl(), library).ifPresent(responseDto -> updateLibraryTotalBooks(library, responseDto));

        log.info("{} 총 도서수 업데이트 종료", library.getName());
    }

    @Transactional
    private void updateLibraryTotalBooks(Library library, ResponseDto responseDto) {
        library.updateTotalBooks(responseDto.getTotalBooks());
        libraryRepository.save(library);
    }

}
