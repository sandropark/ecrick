package com.ecrick.crawler.domain;

import com.ecrick.domain.entity.Library;
import com.ecrick.domain.repository.LibraryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class Crawler {
    private final LibraryRepository libraryRepository;
    private final CrawlerClient crawlerClient;

    @Transactional
    public void crawl() {
        // 1. DB에서 도서관 조회
        List<Library> libraries = libraryRepository.findAll();
        // 2. Health Check 및 총 도서수 업데이트 - 여기부터 병렬로 진행
        libraries.parallelStream()
                .forEach(crawlerClient::updateTotalBooks);
        // 2-1. 실패 시 알림 받기
        // 3. 업데이트된 도서 수로 요청해야 하는 url 생성
        // 4. url목록을 DB에 저장
        // 5. url목록을 조회 후 데이터 요청
        // 6. 데이터를 url과 함께 저장
        // 7. 완료 후 결과 알림 받기
    }


}
