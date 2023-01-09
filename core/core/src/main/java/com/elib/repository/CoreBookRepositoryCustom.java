package com.elib.repository;

import com.elib.domain.CoreBook;

import java.util.List;

public interface CoreBookRepositoryCustom {
    List<CoreBook> findAllDuplicateDate();
    List<CoreBook> findByTitleAndAuthorAndPublisher(String title, String author, String publisher);
}
