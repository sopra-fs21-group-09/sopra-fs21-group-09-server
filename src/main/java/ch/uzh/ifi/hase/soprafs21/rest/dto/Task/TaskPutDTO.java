package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;

import java.util.List;

public class TaskPutDTO {

    private String name;
    private String description;
    private List<TaskPostDTO> subTasks;
    private DeadlinePostDTO deadline;

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

    public List<TaskPostDTO> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<TaskPostDTO> subTasks) {
        this.subTasks = subTasks;
    }

    public DeadlinePostDTO getDeadline() {
        return deadline;
    }

    public void setDeadline(DeadlinePostDTO deadline) {
        this.deadline = deadline;
    }
}
