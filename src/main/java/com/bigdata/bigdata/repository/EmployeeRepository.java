package com.bigdata.bigdata.repository;

import com.bigdata.bigdata.model.Employee;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EmployeeRepository extends CassandraRepository<Employee, Integer> {
}
