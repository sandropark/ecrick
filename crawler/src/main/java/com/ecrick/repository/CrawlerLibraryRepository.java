package com.ecrick.repository;

import com.ecrick.core.dto.QVendorDto;
import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.QLibraryCrawlerDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ecrick.core.domain.QLibrary.library;
import static com.ecrick.core.domain.QVendor.vendor;


@RequiredArgsConstructor
//@Repository
public class CrawlerLibraryRepository {

    private final JPAQueryFactory queryFactory;

    public List<LibraryCrawlerDto> findAll() {
        return queryFactory.select(
                        new QLibraryCrawlerDto(
                                library.id,
                                library.name,
                                library.url,
                                library.param,
                                library.totalBooks,
                                new QVendorDto(library.vendor.id, library.vendor.name, library.vendor.totalBooks),
                                library.contentType,
                                library.size
                        )
                ).from(library)
                .join(library.vendor, vendor)
                .fetch();
    }
}
