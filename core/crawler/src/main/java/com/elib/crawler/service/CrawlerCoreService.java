package com.elib.crawler.service;

import com.elib.crawler.repository.JdbcTemplateBookRepository;
import com.elib.crawler.repository.JdbcTemplateCoreRepository;
import com.elib.domain.Book;
import com.elib.domain.Core;
import com.elib.dto.CoreDto;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CrawlerCoreService {
    private final JdbcTemplateCoreRepository batchCoreRepository;
    private final JdbcTemplateBookRepository batchBookRepository;
    private final CoreRepository coreRepository;
    private final BookRepository bookRepository;

    public Map<String, Long> getTotalCoreAndBook() {
        Map<String, Long> totalMap = new HashMap<>();
        totalMap.put("core", coreRepository.count());
        totalMap.put("book", bookRepository.count());
        return totalMap;
    }

    @Transactional
    public void saveAll(List<CoreDto> coreDtos) {
        ArrayList<Core> cores = new ArrayList<>();
        coreDtos.forEach(coreDto -> cores.add(coreDto.toEntity()));
        batchCoreRepository.saveAll(cores);
    }

    @Transactional
    public void insertBooksFromCoreAndSetBookId() {
        // 가장 처음인 경우 : Core의 모든 BookId = null && Book에 데이터가 없는 경우

        if (coreRepository.isAllBookIdNull() && bookRepository.isEmpty()) {
            // 1. bookId가 null인 core에서 책 뽑아서 저장 (출간일은 가장 최신만 유지)
            batchBookRepository.insertFromCore();

            // 2. core와 book 조인 후 core에 bookId 업데이트
            batchCoreRepository.updateBookIdAll();
        } else {
//             Book에 데이터가 있고 Core에도 연관관계가 맺어있는데 Core에 새로운 데이터가 추가된 경우
//             1. core중 BookId가 null인 데이터만 필터링
            List<Core> newCores = coreRepository.findNewAll();

            // TODO : 벌크로 한 번에 처리하게 수정하기. 일일이하는 것은 너무 오래걸린다.

            for (Core newCore : newCores) {
                // 2. 제목, 저자, 출판사로 Book을 조회해서
                Book book = bookRepository.findByCore(newCore);
                // 2-1. 데이터가 없으면 책 저장
                if (book == null) {
                    book = Book.fromCore(newCore);
                    bookRepository.save(book);
                } else {
                    // 2-2. core > book -> book의 출간일을 수정
                    if (newCore.getPublicDate().isAfter(book.getPublicDate())) {
                        book.updateDate(newCore.getPublicDate());
                    }
                }
                // 3. book을 core.book으로 매핑
                newCore.updateBook(book);
            }
        }
    }

    @Transactional
    public void reduceDuplicateFromDate() {
        // TODO : 수정하기
        // 1. 중복 데이터를 가진 코어를 모두 가져온다.

        // 2. 연관관계를 통일한다.
//        Set<Book> oldBooks = cleanDbService.updateToLatestBook();

        // 3. 연관관계를 잃은 (출간일이 최신이 아닌) Book을 모두 삭제한다. 이로써 중복이 제거되었다.
//        bookRepository.deleteAll(oldBooks);
    }
}
