package sandro.elib.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sandro.elib.domain.Book;
import sandro.elib.domain.EbookService;
import sandro.elib.domain.Library;
import sandro.elib.domain.Relation;
import sandro.elib.dto.BookDetailDto;
import sandro.elib.dto.LibraryEbookServiceDto;
import sandro.elib.repository.BookRepository;
import sandro.elib.repository.EbookServiceRepository;
import sandro.elib.repository.LibraryRepository;
import sandro.elib.repository.RelationRepository;
import sandro.elib.service.BookService;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BookServiceTest {

    @Autowired
    BookService bookService;
    @Autowired BookRepository bookRepository;
    @Autowired LibraryRepository libraryRepository;
    @Autowired EbookServiceRepository ebookServiceRepository;
    @Autowired RelationRepository relationRepository;
    @Autowired EntityManager em;

    Book book;

    @BeforeEach
    void setUp() {
        book = Book.of("사피엔스");
        bookRepository.save(book);

        Library library = Library.of("산들도서관");
        libraryRepository.save(library);

        EbookService ebookService = EbookService.of("교보");
        ebookServiceRepository.save(ebookService);

        Relation relation = Relation.of(book, library, ebookService);
        relationRepository.save(relation);
    }

    @Test
    void findBookDetail() throws Exception {
        em.clear();

        BookDetailDto bookDetail = bookService.getBookDetail(book.getId());
        List<LibraryEbookServiceDto> location = bookDetail.getLocation();
        for (LibraryEbookServiceDto libraryEbookServiceDto : location) {
            System.out.println("libraryEbookServiceDto.getEbookService() = " + libraryEbookServiceDto.getEbookService());
            System.out.println("libraryEbookServiceDto.getLibrary() = " + libraryEbookServiceDto.getLibrary());
        }
    }

}