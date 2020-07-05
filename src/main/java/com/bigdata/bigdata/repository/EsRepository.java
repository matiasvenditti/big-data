package com.bigdata.bigdata.repository;

import com.bigdata.bigdata.model.Log;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsRepository extends ElasticsearchRepository<Log, String> {
    @Query("{\n" +
            "    \"query\": {\n" +
            "        \"bool\": {\n" +
            "            \"must\": [\n" +
            "                {\n" +
            "                    \"range\": {\n" +
            "                        \"@timestamp\": {\n" +
            "                            \"gt\": \"?0\"\n" +
            "                        }\n" +
            "                    }\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    }\n" +
            "}")
    public Page<Log> getLog(String date, Pageable pageable);
}
