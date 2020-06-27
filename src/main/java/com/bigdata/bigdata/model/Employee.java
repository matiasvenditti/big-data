package com.bigdata.bigdata.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@AllArgsConstructor
@Data
@Table("employees")
public class Employee {
    @PrimaryKey
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
