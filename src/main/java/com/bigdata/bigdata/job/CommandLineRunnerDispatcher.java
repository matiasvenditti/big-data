package com.bigdata.bigdata.job;

import com.bigdata.bigdata.BigdataApplication;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommandLineRunnerDispatcher implements CommandLineRunner, ApplicationContextAware {

    private static final String EXECUTE_JOB = "executeJob";
    private final JobRunner job;
    private ConfigurableApplicationContext context;


    @Autowired
    public CommandLineRunnerDispatcher(JobRunner job) {
        this.job = job;
    }

    @Override
    public void run(String... args) throws Exception {
        final List<String> argsList = Arrays.asList(args);
        if (argsList.contains(EXECUTE_JOB)){
            job.run();
            context.close();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = (ConfigurableApplicationContext) applicationContext;
    }
}
