package com.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.elib.domain.EbookService;

public interface EbookServiceRepository extends JpaRepository<EbookService, Long> {
    EbookService findByName(String name);
}
