package com.elib.crawler.healthcheck;

import com.elib.repository.LibraryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.Executor;

@Slf4j
@ActiveProfiles("prd")
@SpringBootTest(properties = {
        "OCI_DB_HOST=jdbc:mariadb://localhost:3306/elib",
        "OCI_DB_USERNAME=root",
        "OCI_DB_PASSWORD=8989"
})
class HealthCheckTestSupport {

    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    HealthCheckTestClient crawlerClient;
    @Autowired
    Executor executor;

}
