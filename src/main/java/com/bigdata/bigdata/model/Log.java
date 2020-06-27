package com.bigdata.bigdata.model;

import com.bigdata.bigdata.DTO.LogDTO;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
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

    public Log(LogDTO logDTO) {
        id = Uuids.timeBased();
        destinationGeoLocation = logDTO.getDestinationGeoLocation();
        sourceGeoLocation = logDTO.getSourceGeoLocation();
        destinationPort = logDTO.getDestinationPort();
        sourceIP = logDTO.getSourceIP();
        message = logDTO.getMessage();
    }
}
