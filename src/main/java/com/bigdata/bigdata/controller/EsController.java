package com.bigdata.bigdata.controller;

import com.bigdata.bigdata.model.Log;
import com.bigdata.bigdata.repository.EsRepository;
import com.bigdata.bigdata.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class EsController {

    private final EsRepository esRepository;
    private final LogRepository logRepository;

    @Autowired
    public EsController(EsRepository esRepository, LogRepository logRepository) {
        this.esRepository = esRepository;
        this.logRepository = logRepository;
    }

    @GetMapping(path = "/es")
    public ResponseEntity<List<Log>> queryElasticSearch() {
        Optional<Log> optional = logRepository.findLatestRecord();
        if (optional.isPresent()) {
            Pageable pageable = PageRequest.of(0, 10);
            List<Log> queryResults = esRepository.findLatestBatch(optional.get().getTimestamp(), pageable);
            return new ResponseEntity<>(queryResults, HttpStatus.OK);
        }

        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
    }
}
