//package com.ecrick.infrastructure;
//
//import com.ecrick.repository.JpaBookRepository;
//import com.ecrick.dto.BookListDto;
//import com.ecrick.dto.Search;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Disabled
//@ActiveProfiles("prd")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//class JpaBookRepositoryTest {
//
//    @Autowired
//    JpaBookRepository jpaBookRepository;
//
//    @Test
//    void search() throws Exception {
//        Page<BookListDto> page = jpaBookRepository.searchPage(new Search(null, "토지"), PageRequest.ofSize(20));
//
//        assertThat(page.getContent()).hasSize(20);
//    }
//}
