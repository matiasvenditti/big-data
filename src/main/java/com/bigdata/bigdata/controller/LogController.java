package com.bigdata.bigdata.controller;

import com.bigdata.bigdata.DTO.LogDTO;
import com.bigdata.bigdata.model.Log;
import com.bigdata.bigdata.repository.LogRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class LogController {

    @Autowired
    LogRepository logRepository;

    @GetMapping("/logs")
    public ResponseEntity<List<Log>> getLogs() {
        List<Log> logs = logRepository.findAll();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/log/{id}")
    public ResponseEntity<Log> getLog(@PathVariable UUID id) {
        Optional<Log> optionalLog = logRepository.findById(id);
        return optionalLog.map(log -> new ResponseEntity<>(log, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/log/{id}")
    public ResponseEntity<Log> updateLog(@RequestBody LogDTO newLog, @PathVariable UUID id) {
        Optional<Log> optionalLog = logRepository.findById(id);
        if (optionalLog.isPresent()) {
            Log log = optionalLog.get();
            log.setDestinationGeoLocation(newLog.getDestinationGeoLocation());
            log.setSourceGeoLocation(newLog.getSourceGeoLocation());
            log.setDestinationPort(newLog.getDestinationPort());
            log.setSourceIP(newLog.getSourceIP());
            log.setSourcePort(newLog.getSourcePort());
            log.setMessage(newLog.getMessage());

            Log updated = logRepository.save(log);
            return new ResponseEntity<>(updated, HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/log/{id}")
    public ResponseEntity<Log> deleteLog(@PathVariable UUID id) {
        Optional<Log> optionalLog = logRepository.findById(id);
        if (optionalLog.isPresent()) {
            logRepository.deleteById(id);
            return new ResponseEntity<>(optionalLog.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/log")
    public ResponseEntity<Log> addLog(@RequestBody LogDTO logDTO) {
        Log log = new Log(logDTO);
        Log saved = logRepository.save(log);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PostMapping("/logs")
    public ResponseEntity<List<Log>> addLogs(@RequestBody List<LogDTO> logs) {
        List<Log> save = logs.stream().map(Log::new).collect(Collectors.toList());
        List<Log> result = logRepository.saveAll(save);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
