package com.bigdata.bigdata.factory;

import com.bigdata.bigdata.DTO.LogDTO;
import com.bigdata.bigdata.model.Log;
import com.datastax.oss.driver.api.core.uuid.Uuids;

import java.time.LocalDateTime;
import java.util.Date;

public class LogFactory {

    public static Log LogByLogDTO(LogDTO logDTO) {
        return new Log(
                Uuids.timeBased(),
                logDTO.getDestinationGeoLocation(),
                logDTO.getSourceGeoLocation(),
                logDTO.getDestinationPort(),
                logDTO.getSourceIP(),
                logDTO.getSourcePort(),
                logDTO.getMessage(),
                LocalDateTime.now()
        );
    }
}
