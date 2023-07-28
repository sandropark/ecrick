package com.ecrick.core.repository;

import com.ecrick.domain.Library;
import com.ecrick.repository.LibraryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LibraryRepositoryTest {

    @Autowired
    LibraryRepository libraryRepository;
    @Autowired EntityManager em;

    @DisplayName("도서관 명을 중복없이 반환한다.")
    @Test
    void findAllNames() throws Exception {
        libraryRepository.saveAndFlush(Library.builder().name("종로").build());
        libraryRepository.saveAndFlush(Library.builder().name("종로").build());

        em.clear();

        List<String> names = libraryRepository.findAllNames();
        assertThat(names).hasSize(1);
    }

    @DisplayName("도서관을 저장할 때 총 도서수와 저장된 도서수를 지정하지 않으면 기본값은 0이다.")
    @Test
    void defaultValue() throws Exception {
        Library library = libraryRepository.saveAndFlush(Library.builder().name("종로").build());

        em.clear();

        Library findLibrary = libraryRepository.findById(library.getId()).get();
        assertThat(findLibrary.getTotalBooks()).isZero();
        assertThat(findLibrary.getSavedBooks()).isZero();
    }
}