package sandro.elib.elib.crwaler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.domain.EbookService;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.domain.Relation;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.repository.BookRepository;
import sandro.elib.elib.repository.EBookServiceRepository;
import sandro.elib.elib.repository.LibraryRepository;
import sandro.elib.elib.repository.RelationRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CrawlerService {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final EBookServiceRepository EBookServiceRepository;
    private final RelationRepository relationRepository;
    private final Crawler crawler;

    @Transactional
    public void crawl(Library library) {
        // TODO : save 시 벌크 연산 적용하기
        EbookService service = EBookServiceRepository.findByName("교보");
        List<BookDto> bookDtos = crawler.crawlByLibrary(library);
        if (bookDtos.isEmpty()) {
            return;
        }
        List<Book> books = bookDtos.stream().map(bookDto -> {
            Book book = bookRepository.findByDto(bookDto);
            if (book == null) {
                Book bookFromDto = bookDto.toEntity();
                bookRepository.save(bookFromDto);
                return bookFromDto;
            }
            if (book.getPublicDate() == null && bookDto.getPublicDate() != null) {
                book.updatePublicDate(bookDto.getPublicDate());
            }
            return book;
        }).collect(toList());

        books.forEach(book -> {
            Relation relation = Relation.of(book, library, service);
            if (relationRepository.notExist(relation)) {
                relationRepository.save(relation);
            }
        });

        int savedBooks = relationRepository.findSavedBooksByLibrary(library);
        library.setSavedBooks(savedBooks);
    }

//    @Transactional
//    public void updateTotalBooks() {
//        /* 1. 도서관 전체 가져오기
//         * 2. api url을 이용해서 전체 도서수 가져오기.
//         * 3. 도서관 엔티티 수정
//         */
//
//        List<Library> libraries = libraryRepository.findAll().stream()
//                .filter(library -> library.getTotalBooks() == null)
//                .collect(toList());
//
//        libraries.forEach(library -> {
//            try {
//                Response response = crawler.requestUrlAndGetResponse(library.getApiUrl());
//                ResponseDto responseDto = crawler.getResponseDto(response);
//                library.setTotalBooks(responseDto.getTotalBooks());
//            } catch (JsonProcessingException e) {
//                log.warn("Json 파싱 실패 - 도서관 = {}", library.getName());
//            } catch (JAXBException e) {
//                log.warn("xml 파싱 실패 - 도서관 = {}", library.getName());
//            } catch (IOException e) {
//                log.warn("url 접속 실패 - url을 확인하세요. 도서관 = {}, url = {}, apiUrl = {}", library.getName(), library.getUrl(), library.getApiUrl());
//            }
//        });
//    }

}