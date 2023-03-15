package com.elib.controller;

import com.elib.service.LibraryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired MockMvc mvc;
    @MockBean LibraryService libraryService;

    @DisplayName("[GET] index 페이지")
    @Test
    void index() throws Exception {
        MvcResult result = mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("searchTargets"))
                .andExpect(view().name("index"))
                .andReturn();
        String html = result.getResponse().getContentAsString();
        assertThat(html).contains("name=\"searchTarget\"");
        assertThat(html).contains("value=\"TOTAL\" selected=\"selected\"");
        assertThat(html).contains("value=\"TITLE\"");
        assertThat(html).contains("value=\"AUTHOR\"");
        assertThat(html).contains("value=\"PUBLISHER\"");
    }

    @DisplayName("[GET] 도움말 페이지")
    @Test
    void info() throws Exception {
        mvc.perform(get("/info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("libraryNames"))
                .andExpect(view().name("info"))
        ;
    }

}