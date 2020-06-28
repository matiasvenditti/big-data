package com.bigdata.bigdata.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("logs")
public class Log {
    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
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

    @PrimaryKeyColumn(name = "time", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime timestamp;
}
