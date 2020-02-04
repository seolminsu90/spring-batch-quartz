package com.batch.config.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

import com.batch.domain.Article;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArticleReader implements ItemReader<Article>, StepExecutionListener {
    private int count = 10;

    @Override
    public Article read() throws Exception {
        Article line = new Article();
        if (count > 0) {
            log.info("Reading ...");
            count--;
        } else {
            return null;
        }
        return line;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Reader initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Reader ended.");
        return ExitStatus.COMPLETED;
    }
}