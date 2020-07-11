package com.bigdata.bigdata.service;

import com.bigdata.bigdata.model.Log;
import com.bigdata.bigdata.repository.LogRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.extern.slf4j.Slf4j;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LogService {

    public static final String COULD_NOT_GET_TIMESTAMP_FROM_ES = "Could not get timestamp from es: ";
    public static final String ADDING = "Adding ";
    public static final String OBJECTS = " objects.";
    private static final String TIMESTAMP = "@timestamp";
    private static final String SOURCEASSTRING = "sourceAsString";
    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void saveAllLogs(Iterator<JSONObject> iterator, int pageSize) {
        while (iterator.hasNext()) {
            List<Log> logs = getLogs(iterator, pageSize);
            log.info(ADDING + logs.size() + OBJECTS);
            logRepository.saveAll(logs);
        }
    }

    public Optional<Log> findLatestRecord() {
        return this.logRepository.findLatestRecord();
    }

    private List<Log> getLogs(Iterator<JSONObject> iterator, int pageSize) {
        List<Log> result = new ArrayList<>(pageSize);
        while (iterator.hasNext() && result.size() < pageSize) {
            JSONObject json = iterator.next();
            try {
                String sourceAsString = json.get(SOURCEASSTRING).toString();
                String time = (new JSONObject(sourceAsString).get(TIMESTAMP)).toString();
                LocalDateTime date = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
                result.add(new Log(Uuids.timeBased(), sourceAsString, date));
            } catch (JSONException e) {
                log.error(COULD_NOT_GET_TIMESTAMP_FROM_ES + json.toString(), e);
            }
        }
        return result;
    }
}
