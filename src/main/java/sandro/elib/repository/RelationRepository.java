package sandro.elib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sandro.elib.domain.Library;
import sandro.elib.domain.Relation;

public interface RelationRepository extends JpaRepository<Relation, Long>, RelationRepositoryCustom{

    @Modifying
    @Query("delete from Relation r where r.library = :library")
    int deleteByLibrary(@Param("library") Library library);

}