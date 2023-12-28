package com.ecrick.service;

import com.ecrick.entity.Book;
import com.ecrick.entity.Vendor;
import com.ecrick.repository.JpaBookRepository;
import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import com.ecrick.repository.JpaRowBookRepository;
import com.ecrick.repository.LibraryRepository;
import com.ecrick.common.VendorName;
import com.ecrick.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

import static com.ecrick.common.VendorName.KYOBO;
import static com.ecrick.common.VendorName.YES24;
import static org.assertj.core.api.Assertions.assertThat;

@Import(BookService.class)
@DataJpaTest
class BookServiceTest {

    @Autowired BookService bookService;
    @Autowired
    JpaBookRepository jpaBookRepository;
    @Autowired VendorRepository vendorRepository;
    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    JpaRowBookRepository jpaRowBookRepository;
    @Autowired EntityManager em;

//    @DisplayName("bookId로 조회하면 연관된 도서관과 공급사를 모두 조회한다. 도서관 이름으로 오름차순 정렬")
//    @Test
//    void getBookDetail() throws Exception {
//        Book book = createBook();
//        createCore(book);
//
//        em.flush();
//        em.clear();
//
//        BookDetailDto bookDetail = bookService.getBookDetail(book.getId());
//        List<LocationDto> locationDtos = bookDetail.getLocation();
//
//        assertThat(locationDtos).hasSize(2);
//        assertThat(locationDtos.get(0).getLibraryName()).isEqualTo("강남");
//        assertThat(locationDtos.get(0).getVendorName()).isEqualTo(YES24.getValue());
//        assertThat(locationDtos.get(1).getLibraryName()).isEqualTo("포항");
//        assertThat(locationDtos.get(1).getVendorName()).isEqualTo(KYOBO.getValue());
//    }

    private Book createBook() {
        return jpaBookRepository.save(Book.builder().title("사피엔스").build());
    }

    private void createCore(Book book) {
        jpaRowBookRepository.save(RowBook.builder().title(book.getTitle()).library(createLibrary("포항", KYOBO)).book(book).build());
        jpaRowBookRepository.save(RowBook.builder().title(book.getTitle()).library(createLibrary("강남", YES24)).book(book).build());
    }

    private Library createLibrary(String name, VendorName vendorName) {
        return libraryRepository.save(Library.builder().name(name).vendor(createVendor(vendorName)).build());
    }

    private Vendor createVendor(VendorName vendorName) {
        return vendorRepository.save(Vendor.builder().name(vendorName).build());
    }

}