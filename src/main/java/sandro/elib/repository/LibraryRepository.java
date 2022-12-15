package sandro.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sandro.elib.domain.Library;

import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findByName(String libraryName);

    @Modifying
    @Query("update Library l set l.savedBooks = (select count(r) from Relation r where l = r.library) where l.id = :id" )
    void updateSavedBooks(@Param("id") Long id);

    @Modifying
    @Query("update Library l set l.savedBooks = (select count(r) from Relation r where l = r.library)")
    void updateAllSavedBooks();
}