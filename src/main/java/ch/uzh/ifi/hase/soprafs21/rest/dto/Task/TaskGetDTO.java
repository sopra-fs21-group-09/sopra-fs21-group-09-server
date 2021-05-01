package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;

import java.util.List;
import java.util.Set;

public class TaskGetDTO {
    private String name;
    private String description;
    private List<TaskGetDTO> subTasks;
    private DeadlineGetDTO deadline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskGetDTO> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<TaskGetDTO> subTasks) {
        this.subTasks = subTasks;
    }

    public DeadlineGetDTO getDeadline() {
        return deadline;
    }

    public void setDeadline(DeadlineGetDTO deadline) {
        this.deadline = deadline;
    }
}
