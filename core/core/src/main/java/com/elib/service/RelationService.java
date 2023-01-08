package com.elib.service;

import com.elib.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RelationService {
    private final RelationRepository relationRepository;

}