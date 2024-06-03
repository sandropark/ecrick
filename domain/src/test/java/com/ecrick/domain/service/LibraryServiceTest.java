package com.ecrick.domain.service;

import com.ecrick.domain.entity.Library;
import com.ecrick.domain.repository.LibraryRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Import(LibraryService.class)
@DataJpaTest
class LibraryServiceTest {

    @Autowired
    LibraryService libraryService;
    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    EntityManager em;

    @DisplayName("중복이 없고, 괄호를 제거한 도서관 이름을 오름차순으로 반환한다.")
    @Test
    void getNames() throws Exception {
        libraryRepository.saveAndFlush(Library.builder().name("종로").build());
        libraryRepository.saveAndFlush(Library.builder().name("종로").build());
        libraryRepository.saveAndFlush(Library.builder().name("강남(인문)").build());
        libraryRepository.saveAndFlush(Library.builder().name("강남(경제)").build());

        em.clear();

        List<String> names = libraryService.getNames();
        assertThat(names).hasSize(2);
        assertThat(names.get(0)).isEqualTo("강남");
        assertThat(names.get(1)).isEqualTo("종로");
    }
}