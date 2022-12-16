package com.elib.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.elib.dto.LibraryDto;
import com.elib.repository.LibraryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @InjectMocks
    LibraryService libraryService;
    @Mock LibraryRepository libraryRepository;

    @Test
    void test() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(1, 20);
        given(libraryRepository.findAll(pageable)).willReturn(Page.empty());

        // When
        Page<LibraryDto> libraries = libraryService.searchLibrary(pageable);

        // Then
        assertThat(libraries).isNotNull();
        then(libraryRepository).should().findAll(pageable);
    }

}