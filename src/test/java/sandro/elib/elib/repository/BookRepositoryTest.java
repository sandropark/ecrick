package sandro.elib.elib.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

//    @Test
//    void findAll() throws Exception {
//        // given
//        List<Book> all = bookRepository.findAll();
//
//        // when & then
//        Assertions.assertThat(all.get(0).getTitle()).isEqualTo("사피엔스");
//    }

}