package com.elib.repository;

import com.elib.dto.CoreListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoreRepositoryCustom {
    Page<CoreListDto> searchPage(String keyword, Pageable pageable);
}
