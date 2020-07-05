package com.bigdata.bigdata.job;

import com.bigdata.bigdata.repository.EsRepository;
import com.bigdata.bigdata.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRunner {

    private final EsRepository esRepository;
    private final LogRepository logRepository;

    @Autowired
    public JobRunner(EsRepository esRepository, LogRepository logRepository) {
        this.esRepository = esRepository;
        this.logRepository = logRepository;
    }

    public void run(){

    }
}
