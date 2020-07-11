package com.bigdata.bigdata.job;

import com.bigdata.bigdata.factory.LogFactory;
import com.bigdata.bigdata.model.Log;
import com.bigdata.bigdata.repository.EsRepository;
import com.bigdata.bigdata.repository.LogRepository;
import com.bigdata.bigdata.service.EsServices;
import com.bigdata.bigdata.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;


@Component
@Slf4j
public class JobRunner {

    public static final String EXECUTING_JOB = "Executing Job";
    public static final String EXITITNG_FROM_APPLICATION = "exititng from application";
    private final EsServices esServices;
    private final LogService logService;
    private final int pageSize = 10000;


    @Autowired
    public JobRunner(EsServices esServices, LogService logService) {
        this.esServices = esServices;
        this.logService = logService;
    }

    public void run() {
        log.info(EXECUTING_JOB);

        Optional<Log> optional = logService.findLatestRecord();
        Iterator<JSONObject> all;

        if (optional.isPresent()) {
            LocalDateTime timestamp = optional.get().getTimestamp();
            all = esServices.getNewest(timestamp.toString(), pageSize);
        } else {
            all = esServices.getAll(pageSize);
        }
        logService.saveAllLogs(all, pageSize);

        log.info(EXITITNG_FROM_APPLICATION);
    }
}
