package com.bigdata.bigdata.factory;

import com.bigdata.bigdata.DTO.LogDTO;
import com.bigdata.bigdata.model.Log;
import com.datastax.oss.driver.api.core.uuid.Uuids;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LogFactory {

    public static Log LogByLogDTO(LogDTO logDTO) {
        return new Log(
                Uuids.timeBased(),
                logDTO.getRawJson(),
                LocalDateTime.now()
        );
    }

    public static List<Log> logsByRawJson(List<String> rawJsons) {
        return rawJsons.stream().map(rawJson -> new Log(
                Uuids.timeBased(),
                rawJson,
                LocalDateTime.now()
        )).collect(Collectors.toList());
    }
}
