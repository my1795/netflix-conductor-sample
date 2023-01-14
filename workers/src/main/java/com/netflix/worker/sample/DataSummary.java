package com.netflix.worker.sample;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.worker.model.PopulationElement;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
@Log4j2
public class DataSummary implements Worker {

    private final String taskDefName= "summarize_data";
    public DataSummary() {
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        log.info("inputs " + task.getInputData());
        TaskResult result = new TaskResult(task);
        List<LinkedHashMap> dataList = ((List<LinkedHashMap>) task.getInputData().get("data"));
        List<PopulationElement> populationElements = new ArrayList<>();
        dataList.forEach(dataMap -> {
            PopulationElement populationElement = new PopulationElement();
            populationElement.setNation((String) dataMap.get("Nation"));
            populationElement.setYear( (Integer) dataMap.get("ID Year"));
            populationElement.setPopulation( (Integer) dataMap.get("Population"));
            populationElements.add(populationElement);
        });
        result.addOutputData("data_summary",populationElements);
        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }
}
