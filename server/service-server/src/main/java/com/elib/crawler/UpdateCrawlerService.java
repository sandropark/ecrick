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
@RequiredArgsConstructor
@Service
public class UpdateCrawlerService implements Runnable {

    private final LibraryRepository libraryRepository;
    private Long libraryId;

    public void init(Long libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public void run() {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));

        log.info("{} 총 도서수 업데이트", library.getName());

        ResponseDto responseDto;
        try {
            responseDto = CrawlUtil.responseToDto(CrawlUtil.requestUrl(library.getApiUrl()));
        } catch (JsonProcessingException | JAXBException e) {
            log.error("CrawlerService 파싱 오류 library = {}",library.getName(), e);
            return;
        } catch (IOException e) {
            log.error("error", e);
            return;
        }
        updateLibraryTotalBooks(library, responseDto);
    }

    @Transactional
    private void updateLibraryTotalBooks(Library library, ResponseDto responseDto) {
        library.updateTotalBooks(responseDto.getTotalBooks());
        libraryRepository.save(library);
    }

}
