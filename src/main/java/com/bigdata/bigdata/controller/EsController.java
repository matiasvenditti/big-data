package com.bigdata.bigdata.controller;

import com.bigdata.bigdata.model.Log;
import com.bigdata.bigdata.repository.EsRepository;
import com.bigdata.bigdata.repository.LogRepository;
import com.bigdata.bigdata.service.EsServices;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHits;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class EsController {

    private final EsRepository esRepository;
    private final LogRepository logRepository;
    private final EsServices esServices;

    @Autowired
    public EsController(EsRepository esRepository, LogRepository logRepository, EsServices esServices) {
        this.esRepository = esRepository;
        this.logRepository = logRepository;
        this.esServices = esServices;
    }

    @GetMapping(path = "/es/count")
    public ResponseEntity<Long> queryElasticSearch() {
        final Optional<Long> result = esServices.countAll();

        if (result.isPresent()){
            return new ResponseEntity<>(result.get(),HttpStatus.OK);
        } else return new ResponseEntity<>(-1L, HttpStatus.NOT_FOUND);
    }
    @GetMapping(path = "/es",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAll(){
        final Iterator<JSONObject> iterator = esServices.getAll(5);
        JSONArray array = new JSONArray();
        while (array.length()<10 && iterator.hasNext()){
            array.put(iterator.next());
        }

        return ResponseEntity.ok().body(array.toString());
//        return new ResponseEntity<>(array.toString(),HttpStatus.OK);
    }


}
