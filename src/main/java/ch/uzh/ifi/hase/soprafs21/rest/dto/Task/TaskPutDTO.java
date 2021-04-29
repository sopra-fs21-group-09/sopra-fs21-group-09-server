package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;

import java.util.Set;

public class TaskPutDTO {

    private String name;
    private String description;
    private Set<TaskPostDTO> subTasks;
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

    public Set<TaskPostDTO> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(Set<TaskPostDTO> subTasks) {
        this.subTasks = subTasks;
    }

    public DeadlinePostDTO getDeadline() {
        return deadline;
    }

    public void setDeadline(DeadlinePostDTO deadline) {
        this.deadline = deadline;
    }
}
