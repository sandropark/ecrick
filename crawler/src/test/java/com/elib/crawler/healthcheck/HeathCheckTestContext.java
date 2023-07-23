package com.elib.crawler.healthcheck;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Executor;

@SpringBootApplication(scanBasePackages = "com.elib")
public class HeathCheckTestContext {
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 100;
    private static final int QUEUE_CAPACITY = 15;
    private static final String CUSTOM_THREAD_NAME_PREFIX = "CUSTOM_THREAD-";

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setThreadNamePrefix(CUSTOM_THREAD_NAME_PREFIX);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    public RestTemplate template() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add(getInterceptor());
        return template;
    }

    private ClientHttpRequestInterceptor getInterceptor() {
        return (request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            List<String> contentTypes = response.getHeaders().get("Content-Type");
            if (contentTypes != null && isJustText(contentTypes) || isBookcube(request))
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        };
    }

    private boolean isBookcube(HttpRequest request) {
        return request.getURI().toString().contains("FxLibrary/m/product/productList");
    }

    private boolean isJustText(List<String> contentTypes) {
        return contentTypes.get(0).contains("text;");
    }
}
