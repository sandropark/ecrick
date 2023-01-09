package com.elib.service;

import com.elib.domain.Book;
import com.elib.domain.CoreBook;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CoreBookService {
    private final CoreBookRepository coreBookRepository;
    private final BookRepository bookRepository;
    private final CleanDbService cleanDbService;

    @Transactional
    public void reduceDuplicateFromPublicDate() {
        // 1. coreBook과 book을 조인한 다음 제목,저자,출판사로 group by 해서 중복이 있는 데이터 모두 가져오기
        List<CoreBook> coreBooks = coreBookRepository.findAllDuplicateDate();

        // 2. 그룹마다 출간일 중 가장 최근일자 데이터로 연관관계 모두 수정
        Set<Book> oldBooks = cleanDbService.updateToLatestBook(coreBooks);

        // 3. 연관관계를 잃은 Book 데이터는 모두 삭제
        bookRepository.deleteAll(oldBooks);
    }
}
