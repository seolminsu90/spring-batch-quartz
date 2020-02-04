package com.batch.config.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import com.batch.domain.Article;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArticleProcessor implements
  ItemProcessor<Article, Article>, StepExecutionListener {
 
    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Processor initialized.");
    }
     
    @Override
    public Article process(Article line) throws Exception {
        return line;
    }
 
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Processor ended.");
        return ExitStatus.COMPLETED;
    }

}
