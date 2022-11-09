package sandro.elib.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sandro.elib.elib.domain.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    Library findByName(String libraryName);
    void deleteByName(String libraryName);
}