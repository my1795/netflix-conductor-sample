package com.netflix.worker;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.worker.sample.CourierWorker;
import com.netflix.worker.sample.DataSummary;
import com.netflix.worker.sample.SourceSummary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ConductorApplication {

    public static void main(String[] args) {
        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI("http://conductor-server:8080/api/"); // Point this to the server API

        int threadCount = 4; // number of threads used to execute workers.  To avoid starvation, should be
        // same or more than number of workers
        Set workers = new HashSet<Worker>();

        Worker dataSummary = new DataSummary();
        Worker sourceSummary = new SourceSummary();
        Worker courierWorker = new CourierWorker();

        workers.add(dataSummary);
        workers.add(sourceSummary);
        workers.add(courierWorker);
        // Create TaskRunnerConfigurer
        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, workers)
                        .withThreadCount(threadCount)
                        .build();

        // Start the polling and execution of tasks
        configurer.init();

        SpringApplication.run(ConductorApplication.class, args);
    }
}
