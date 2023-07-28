package com.ecrick.core.repository;

import com.ecrick.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

//@EnableJpaRepositories
@ActiveProfiles("prd")
@SpringBootTest(
        properties = {
                "OCI_DB_HOST=jdbc:mariadb://localhost:3306/elib",
                "OCI_DB_USERNAME=root",
                "OCI_DB_PASSWORD=8989"
        }
)
class SelectTest {

    @Autowired
    LibraryRepository libraryRepository;

    @Test
    void select() throws Exception {
        int size = libraryRepository.findAll().size();
        System.out.println("size = " + size);
    }

    @SpringBootApplication(scanBasePackages = "com.ecrick.")
    static class Context {
    }
}
