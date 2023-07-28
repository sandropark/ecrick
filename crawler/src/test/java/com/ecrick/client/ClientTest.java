package com.ecrick.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@EnableJpaRepositories
@ActiveProfiles("prd")
@SpringBootTest(
//        classes = ClientTest.TestContext.class,
        properties = {
                "OCI_DB_HOST=jdbc:mariadb://localhost:3306/elib",
                "OCI_DB_USERNAME=root",
                "OCI_DB_PASSWORD=8989"
        })
class ClientTest {

    @Autowired
    CrawlerClientService crawlerClientService;

    @Test
    void test() throws Exception {
        crawlerClientService.crawlAll();
    }

    @SpringBootApplication(scanBasePackages = {
            "com.ecrick.client",
            "com.ecrick.core",
    })
    protected static class TestContext {}

}
