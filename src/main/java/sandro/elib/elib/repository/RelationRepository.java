package sandro.elib.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.domain.Relation;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Modifying
    @Query("delete from Relation r where r.library = :library")
    void deleteByLibrary(@Param("library") Library library);

}