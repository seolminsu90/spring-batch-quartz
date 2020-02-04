package com.batch.config.batch;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import com.batch.domain.Article;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArticleWriter implements ItemWriter<Article>, StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Writer initialized.");
    }


    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Writer ended.");
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends Article> items) throws Exception {
    }

}
