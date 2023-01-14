package com.netflix.worker.sample;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.worker.model.PopulationElement;
import com.netflix.worker.model.SourceElement;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.dynamic.scaffold.MethodGraph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Log4j2
public class SourceSummary implements Worker {

    private final String taskDefName= "summarize_source";

    public SourceSummary() {
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        log.info("inputs " + task.getInputData());
        TaskResult result = new TaskResult(task);
        List<LinkedHashMap> sourceList = ((List<LinkedHashMap>)  task.getInputData().get("source"));
        LinkedHashMap annotations = sourceList.get(0);
        LinkedHashMap annotation = (LinkedHashMap) annotations.get("annotations");
        SourceElement sourceElement = new SourceElement();

        sourceElement.setSourceName((String)annotation.get("source_name"));
        sourceElement.setSourceDescription((String)annotation.get("source_description"));
        result.addOutputData("source_summary",sourceElement);
        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }
}
