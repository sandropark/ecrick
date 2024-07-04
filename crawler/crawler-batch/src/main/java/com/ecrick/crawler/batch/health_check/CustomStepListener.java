package com.ecrick.crawler.batch.health_check;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomStepListener implements StepExecutionListener {
    private final FailedLibraryList failedLibraryList;

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Failed Library List: {}", failedLibraryList.getFailedLibraryList());
        return StepExecutionListener.super.afterStep(stepExecution);
    }
}
