package com.bigdata.bigdata.repository;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

public class EsRepository {
    private final RestHighLevelClient esClient;

    @Autowired
    public EsRepository(RestHighLevelClient client) {
        this.esClient = client;
    }

}
