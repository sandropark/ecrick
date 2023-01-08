package com.elib.crawler.service;

import com.elib.crawler.repository.JdbcTemplateCoreRepository;
import com.elib.domain.Core;
import com.elib.dto.CoreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CrawlerCoreService {
    private final JdbcTemplateCoreRepository batchCoreRepository;

    @Transactional
    public void saveAll(List<CoreDto> coreDtos) {
        ArrayList<Core> cores = new ArrayList<>();
        coreDtos.forEach(coreDto -> cores.add(coreDto.toEntity()));
        batchCoreRepository.saveAll(cores);
    }

}
