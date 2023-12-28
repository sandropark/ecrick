package com.ecrick.repository;

import com.ecrick.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RepoTest {

    @Autowired
    JpaBookRepository jpaBookRepository;

    @Test
    void test() throws Exception {
        List<Book> all = jpaBookRepository.findAll();
        System.out.println("all = " + all);
    }
}
