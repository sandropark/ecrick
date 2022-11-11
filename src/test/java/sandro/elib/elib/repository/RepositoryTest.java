package sandro.elib.elib.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.domain.EbookService;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.domain.Relation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class RepositoryTest {

    @Autowired LibraryRepository libraryRepository;
    @Autowired RelationRepository relationRepository;
    @Autowired EBookServiceRepository eBookServiceRepository;
    @Autowired BookRepository bookRepository;

    @Test
    void findAll() throws Exception {
        long count = relationRepository.count();
        System.out.println("count = " + count);
//        count = 4391510
    }

//    @Commit
    @Test
    void deleteLibrary() throws Exception {
//        Library library = libraryRepository.findByName("계명문화대도서관");
//        relationRepository.deleteByLibrary(library);
//        libraryRepository.delete(library);

        libraryRepository.deleteByName("경찰대학 김구도서관");

//        em.flush();
    }

//    @Commit
    @Test
    void insertTestData() throws Exception {
        Book book = Book.of("사피엔스", "하라리", null, null, null);
        Library library = Library.of("테스트도서관");
        EbookService ebookService = eBookServiceRepository.findByName("교보");

        Relation relation = Relation.of(book, library, ebookService);

        bookRepository.save(book);
        libraryRepository.save(library);
        relationRepository.save(relation);
    }

//    @Commit
    @Test
    void uniqueTest() throws Exception {
        Book book = bookRepository.findById(268107L).get();
        Library library = libraryRepository.findByName("테스트도서관");
        EbookService ebookService = eBookServiceRepository.findByName("교보");

        Relation relation = Relation.of(book, library, ebookService);

        relationRepository.save(relation);
    }


    @Commit
    @Test
    void updateSavedBooks() throws Exception {
        List<Library> libraries = libraryRepository.findAll();
        libraries.forEach(library -> {
            int savedBooks = relationRepository.findSavedBooksByLibrary(library);
            library.setSavedBooks(savedBooks);
        });
    }

}