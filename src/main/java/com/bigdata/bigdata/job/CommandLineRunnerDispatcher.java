package com.bigdata.bigdata.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommandLineRunnerDispatcher implements CommandLineRunner {

    private final JobRunner job;

    @Autowired
    public CommandLineRunnerDispatcher(JobRunner job) {
        this.job = job;
    }

    @Override
    public void run(String... args) throws Exception {
        final List<String> argsList = Arrays.asList(args);
        if (argsList.contains("executeJob")) job.run();
    }
}
