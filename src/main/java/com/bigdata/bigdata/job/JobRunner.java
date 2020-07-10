package com.bigdata.bigdata.job;

import com.bigdata.bigdata.factory.LogFactory;
import com.bigdata.bigdata.repository.EsRepository;
import com.bigdata.bigdata.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;


@Component
@Slf4j
public class JobRunner {

    public static final String EXECUTING_JOB = "executing job";
    public static final String EXITITNG_FROM_APPLICATION = "exititng from application";
    private final EsRepository esRepository;
    private final LogRepository logRepository;


    @Autowired
    public JobRunner(EsRepository esRepository, LogRepository logRepository) {
        this.esRepository = esRepository;
        this.logRepository = logRepository;
    }

    public void run() {
        log.info(EXECUTING_JOB);

        try {
            log.info("Sleeping for 10 sec");
            sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info(EXITITNG_FROM_APPLICATION);
    }
}
