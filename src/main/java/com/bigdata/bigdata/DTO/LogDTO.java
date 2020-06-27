package com.bigdata.bigdata.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {

    private String destinationGeoLocation;
    private String sourceGeoLocation;
    private int destinationPort;
    private String sourceIP;
    private int sourcePort;
    private String message;

}
