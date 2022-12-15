package sandro.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sandro.elib.domain.EbookService;

public interface EbookServiceRepository extends JpaRepository<EbookService, Long> {
    EbookService findByName(String name);
}
