package com.ecrick.service;

import com.ecrick.entity.RowBook;

import java.util.List;

public interface CrawlerModel {
    Integer getTotalBooks();

    List<RowBook> getRowBooks();
}
