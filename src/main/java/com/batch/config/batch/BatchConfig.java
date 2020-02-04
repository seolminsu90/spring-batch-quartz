package com.batch.config.batch;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.domain.Article;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private int size = 10;

    /*@Bean
    @StepScope
    public ItemReader<Article> itemReader() {
        return new ArticleReader();
    }

    @Bean
    @JobScope
    protected Step processArticles(ItemReader<Article> reader, ItemProcessor<Article, Article> processor,
            ItemWriter<Article> writer) {
        return steps.get("articleProcess").<Article, Article>chunk(20).reader(reader).processor(processor).writer(writer)
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Article> itemWriter() {
        return new ArticleWriter();
    }

    @Bean
    @StepScope
    public ItemProcessor<Article, Article> itemProcessor() {
        return new ArticleProcessor();
    }
    
    @Bean
    public Job job() {
        return jobs.get("chunksJob" + System.currentTimeMillis())
                .start(processArticles(itemReader(), itemProcessor(), itemWriter())).build();
    }*/

    
    @Bean
    @JobScope
    public Step articlePagingStep() {
        return steps.get("articlePagingStep")
                .<Article, Article>chunk(size)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Article> itemReader() {
        log.info("Reading...");
        return new JpaPagingItemReaderBuilder<Article>()
                .queryString("SELECT A FROM Article A") // Mapping Class Name을 대상으로 함, Return 대상이 Article이어야함.
                .pageSize(size)
                .entityManagerFactory(entityManagerFactory)
                .name("articlePagingReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Article, Article> itemProcessor() {
        log.info("Processing...");
        return item -> {
            item.setTestcnt(item.getTestcnt() + 1);
            return item;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Article> itemWriter() {
        log.info("Writing...");
        JpaItemWriter<Article> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    
    @Bean
    public Job payPagingJob() {
        return jobs.get("chunksJob - JPA" + System.currentTimeMillis())
                .start(articlePagingStep())
                .build();
    }

}
