package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;

import java.util.Set;

public class TaskGetDTO {
    private String name;
    private String description;
    private Set<TaskGetDTO> subTasks;
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

    public Set<TaskGetDTO> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(Set<TaskGetDTO> subTasks) {
        this.subTasks = subTasks;
    }

    public DeadlineGetDTO getDeadlineGetDTO() {
        return deadline;
    }

    public void setDeadlineGetDTO(DeadlineGetDTO deadlineGetDTO) {
        this.deadline = deadlineGetDTO;
    }
}
