package com.bigdata.bigdata.repository;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

public class EsRepository {
    private final RestHighLevelClient client;

    @Autowired
    public EsRepository(RestHighLevelClient client) {
        this.client = client;
    }

}
