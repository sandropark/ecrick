package com.ecrick.crawler.batch.health_check;

import com.ecrick.domain.entity.Library;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class FailedLibraryList {
    private final List<FailedLibrary> failedLibraryList = new ArrayList<>();

    public synchronized void add(FailedLibrary failedLibrary) {
        failedLibraryList.add(failedLibrary);
    }

    public String getListAsString() {
        StringBuilder sb = new StringBuilder();
        for (FailedLibrary failedLibrary : failedLibraryList) {
            Library library = failedLibrary.library();
            sb.append(library.getName())
                    .append(" url: ")
                    .append(library.getUrl())
                    .append(" error: ")
                    .append(failedLibrary.e().getMessage())
                    .append("\n");
        }
        return sb.toString();
    }

    @Builder
    public record FailedLibrary(Library library, Exception e) {
    }
}
