package com.elib.repository;

import com.elib.domain.EbookService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EbookServiceRepository extends JpaRepository<EbookService, Long> {
    EbookService findByName(String name);
}
