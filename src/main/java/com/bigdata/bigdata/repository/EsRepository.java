package com.bigdata.bigdata.repository;

import com.bigdata.bigdata.model.Log;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface EsRepository extends Repository<Log, String> {
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
    List<Log> findLatestBatch(String date, Pageable pageable);
}
