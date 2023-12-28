package com.ecrick.crawler.domain.webclient.response;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;

import java.util.List;

public interface LibraryResponse {
    Integer getTotalBooks();
    List<RowBook> toRowBooks(Library library);
}