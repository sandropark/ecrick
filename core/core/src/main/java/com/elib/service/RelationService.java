package com.elib.service;

import com.elib.domain.Relation;
import com.elib.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RelationService {
    private final RelationRepository relationRepository;

    @Transactional
    public void saveNotExists(Relation relation) {
        if (!relationRepository.existsByBookAndLibraryAndVendor(relation.getBook(), relation.getLibrary(), relation.getVendor())) {
            relationRepository.save(relation);
        }
    }
}