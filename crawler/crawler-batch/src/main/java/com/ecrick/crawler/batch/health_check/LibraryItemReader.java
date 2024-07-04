package com.ecrick.crawler.batch.health_check;

import com.ecrick.domain.entity.Library;
import com.ecrick.domain.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LibraryItemReader extends AbstractPagingItemReader<Library> {
    private final LibraryRepository libraryRepository;

    @Override
    protected void doReadPage() {
        setMaxItemCount(30);
        setPageSize(100);
        results = libraryRepository.findAll(PageRequest.of(getPage(), getPageSize())).getContent();
    }
}
