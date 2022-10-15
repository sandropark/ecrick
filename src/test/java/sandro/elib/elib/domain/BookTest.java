package sandro.elib.elib.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.dto.LibraryServiceDto;
import sandro.elib.elib.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional(readOnly = true)
class BookTest {
    @Autowired BookRepository bookRepository;

    @Test
    void createLocation() throws Exception {
        List<Book> all = bookRepository.findAll();
        Book book = all.get(0);

        List<LibraryServiceDto> collect = book.getBookLibraryServices().stream()
                .map(b -> new LibraryServiceDto(b.getLibrary().getName(), b.getService().getName()))
                .collect(Collectors.toList());

        for (LibraryServiceDto libraryServiceDto : collect) {
            System.out.println("libraryServiceDto = " + libraryServiceDto);
        }
    }
}