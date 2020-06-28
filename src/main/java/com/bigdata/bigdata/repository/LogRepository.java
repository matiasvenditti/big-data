package com.bigdata.bigdata.repository;

import com.bigdata.bigdata.model.Log;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LogRepository extends CassandraRepository<Log, UUID> {

    @Query("select * from logs limit 1")
    Optional<Log> findLatestRecord();
}
