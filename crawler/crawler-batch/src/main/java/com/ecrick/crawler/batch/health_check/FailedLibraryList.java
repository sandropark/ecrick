package com.ecrick.crawler.batch.health_check;

import com.ecrick.domain.entity.Library;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class FailedLibraryList {
    private final List<Library> failedLibraryList = new ArrayList<>();

    public synchronized void add(Library library) {
        failedLibraryList.add(library);
    }
}
