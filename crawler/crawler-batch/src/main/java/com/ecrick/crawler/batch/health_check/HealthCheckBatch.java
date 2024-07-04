package com.ecrick.crawler.batch.health_check;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.domain.exception.CrawlerException;
import com.ecrick.domain.entity.Library;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.concurrent.Future;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class HealthCheckBatch {
    private final JobRepository jobRepository;
    private final JpaTransactionManager transactionManager;
    private final LibraryItemReader libraryReader;
    private final HealthCheckItemProcessor healthCheckItemProcessor;
    private final TaskExecutor taskExecutor;

    private final CustomStepListener customStepListener;

    @Bean
    public Job healthCheckJob() {
        return new JobBuilder("healthCheckJob", jobRepository).start(healthCheckStep()).build();
    }

    @Bean
    public Step healthCheckStep() {
        // 스텝이 끝나고 healthCheck이 실패한 도서관 목록을 정리해서 알림
        return new StepBuilder("healthCheckStep", jobRepository)
                .<Library, Future<ResponseDto>>chunk(100, transactionManager)
                .reader(libraryReader)
                .processor(asyncItemProcessor())
                .writer(asyncItemWriter())
                .faultTolerant()
                .skip(CrawlerException.class)
                .skipLimit(10)
                .listener(customStepListener)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public AsyncItemProcessor<Library, ResponseDto> asyncItemProcessor() {
        AsyncItemProcessor<Library, ResponseDto> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setTaskExecutor(taskExecutor);
        asyncItemProcessor.setDelegate(healthCheckItemProcessor);
        return asyncItemProcessor;
    }

    @Bean
    public AsyncItemWriter<ResponseDto> asyncItemWriter() {
        AsyncItemWriter<ResponseDto> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(items ->
                items.forEach(responseDto ->
                        log.info("totalBooks = {}", responseDto.getTotalBooks())));
        return asyncItemWriter;
    }
}
