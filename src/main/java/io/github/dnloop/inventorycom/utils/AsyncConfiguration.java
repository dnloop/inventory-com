package io.github.dnloop.inventorycom.utils;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {
	private static final Log log = LogFactory.getLog(AsyncConfiguration.class);

	@Bean(name = "taskExecutor")

	public Executor taskExecutor() {

		log.debug("[ Creating Async Task Executor ]");

		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(2);

		executor.setMaxPoolSize(10);

		executor.setQueueCapacity(100);

		executor.initialize();

		return executor;

	}

}
