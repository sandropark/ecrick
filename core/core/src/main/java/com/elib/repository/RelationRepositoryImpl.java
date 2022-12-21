package com.elib.repository;

import com.elib.domain.Relation;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@RequiredArgsConstructor
public class RelationRepositoryImpl implements RelationRepositoryCustom{

    private final EntityManager em;

    @Override
    public boolean notExist(Relation relation) {
        try {
            em.createQuery("select r from Relation r where r.book = :book and r.library = :library and r.ebookService = :ebookService", Relation.class)
                    .setParameter("book", relation.getBook())
                    .setParameter("library", relation.getLibrary())
                    .setParameter("ebookService", relation.getEbookService())
                    .getSingleResult();
            return false;
        } catch (NoResultException e) {
            return true;
        }
    }
}