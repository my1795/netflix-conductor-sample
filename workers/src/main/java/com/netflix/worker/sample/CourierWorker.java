package com.netflix.worker.sample;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

@Log4j2
public class CourierWorker implements Worker {

    private final String taskDefName= "assign_order_to_courier";
    private final String[] PIZZA_NAMES = {"NEAPOLITAN","PEPPERONI","MARGHERITA"};
    public CourierWorker() {
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        log.info("inputs " + task.getInputData());
        TaskResult result = new TaskResult(task);
        String pizzaName = (String) task.getInputData().get("pizzaName");

        if(Arrays.stream(PIZZA_NAMES).anyMatch(p -> p.equals(pizzaName))){
            result.addOutputData("courier","JOHN");
        }
        else {
            result.addOutputData("courier","MIKE");
        }

        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }
}
