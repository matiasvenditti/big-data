package com.bigdata.bigdata.model;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@Data
@Table("logs")
public class Log {
    @PrimaryKey
    private UUID id;

    @Column("DestinationGeoLocation")
    private String destinationGeoLocation;

    @Column("SourceGeoLocation")
    private String sourceGeoLocation;

    @Column("dest_port")
    private int destinationPort;

    @Column("SourceIP")
    private String sourceIP;

    @Column("src_port")
    private int sourcePort;

    @Column("message")
    private String message;
}
