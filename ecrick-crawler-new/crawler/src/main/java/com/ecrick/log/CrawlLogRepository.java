package com.ecrick.log;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CrawlLogRepository extends JpaRepository<CrawlLog, Long> {
    @EntityGraph(attributePaths = {"library"})
    List<CrawlLog> findAllByTransactionId(UUID transactionId);
}
