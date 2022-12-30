package com.elib.crawler;

import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;
import com.elib.repository.LibraryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.JAXBException;
import java.io.IOException;

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
        ResponseDto responseDto = getResponseDto(library);
        if (responseDto != null) {
            updateLibraryTotalBooks(library, responseDto);
        }

        log.info("{} 총 도서수 업데이트 종료", library.getName());
    }

    private ResponseDto getResponseDto(Library library) {
        try {
            return CrawlerUtil.responseToDto(CrawlerUtil.requestUrl(library.getUrl()));
        } catch (JAXBException | JsonProcessingException e) {
            log.error("CrawlerService 파싱 오류 library = {}", library.getName(), e);
            return null;
        } catch (IOException e) {
            log.error("{} error", library.getName(), e);
            return null;
        }
    }

    @Transactional
    private void updateLibraryTotalBooks(Library library, ResponseDto responseDto) {
        library.updateTotalBooks(responseDto.getTotalBooks());
        libraryRepository.save(library);
    }

}
