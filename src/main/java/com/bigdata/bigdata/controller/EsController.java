package com.bigdata.bigdata.controller;

import com.bigdata.bigdata.model.Log;
import com.bigdata.bigdata.repository.EsRepository;
import com.bigdata.bigdata.repository.LogRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class EsController {

    private final EsRepository esRepository;
    private final LogRepository logRepository;
    private final RestHighLevelClient client;

    @Autowired
    public EsController(EsRepository esRepository, LogRepository logRepository,RestHighLevelClient client) {
        this.esRepository = esRepository;
        this.logRepository = logRepository;
        this.client = client;
    }

    @GetMapping(path = "/es")
    public ResponseEntity<String> queryElasticSearch() {
        Optional<Log> optional = logRepository.findLatestRecord();
        if (optional.isPresent()) {
            Pageable pageable = PageRequest.of(0, 10);
            List<Log> queryResults = esRepository.findLatestBatch(optional.get().getTimestamp(), pageable);
            return new ResponseEntity<>(queryResults.toString(), HttpStatus.OK);
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("shorewall");

        try {
            final SearchHits hits = client.search(searchRequest, RequestOptions.DEFAULT).getHits();
            return new ResponseEntity<String>(hits.getHits().toString(),HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }
}
