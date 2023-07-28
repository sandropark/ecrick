package com.ecrick.client;

import com.ecrick.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
        libraryRepository.findAll();
    }

    @SpringBootApplication(scanBasePackages = "com.ecrick.core.repository")
    static class Context {}
}
