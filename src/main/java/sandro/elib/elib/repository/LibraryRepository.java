package sandro.elib.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sandro.elib.elib.domain.Library;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    Library findByName(String libraryName);
    void deleteByName(String libraryName);

    List<Library> findByTotalBooksGreaterThanAndTotalBooksLessThan(int start, int end);
}