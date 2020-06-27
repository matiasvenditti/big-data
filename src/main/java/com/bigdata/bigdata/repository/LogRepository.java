package com.bigdata.bigdata.repository;

import com.bigdata.bigdata.model.Log;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface LogRepository extends CassandraRepository<Log, UUID> {
}
