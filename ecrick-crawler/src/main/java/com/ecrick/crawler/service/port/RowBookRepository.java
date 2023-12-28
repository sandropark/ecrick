package com.ecrick.crawler.service.port;

import com.ecrick.entity.RowBook;

import java.util.List;

public interface RowBookRepository {
    void saveAll(List<RowBook> rowBooks);

    void mapCoreAndBookIfCore_BookIdIsNull();

    void deleteByLibraryId(Long libraryId);

    Long count();
}
