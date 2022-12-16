package com.elib.web;

import com.elib.domain.Library;
import com.elib.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired MockMvc mvc;
    @Autowired LibraryRepository libraryRepository;

    @Test
    void save() throws Exception {

        // When
        mvc.perform(post("/admin/libraries/form")
                        .param("name", "서울")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        Library findLibrary = libraryRepository.findByName("서울")
                .orElseThrow();

        // Then
        assertThat(findLibrary.getName()).isEqualTo("서울");
    }
}
