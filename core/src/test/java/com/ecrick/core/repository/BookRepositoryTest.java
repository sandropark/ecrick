package com.ecrick.core.repository;

import com.ecrick.dto.BookListDto;
import com.ecrick.dto.Search;
import com.ecrick.repository.BookRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@ActiveProfiles("prd")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void search() throws Exception {
        Page<BookListDto> page = bookRepository.searchPage(new Search(null, "토지"), PageRequest.ofSize(20));

        assertThat(page.getContent()).hasSize(20);
    }
}
