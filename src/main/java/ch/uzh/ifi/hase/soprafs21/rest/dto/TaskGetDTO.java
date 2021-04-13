package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Date;
import java.util.Set;

public class TaskGetDTO {
    private String name;
    private String description;
    private Set<TaskPostDTO> subTasks;
    private DeadlineGetDTO deadlineGetDTO;

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

    public DeadlineGetDTO getDeadlineGetDTO() {
        return deadlineGetDTO;
    }

    public void setDeadlineGetDTO(DeadlineGetDTO deadlineGetDTO) {
        this.deadlineGetDTO = deadlineGetDTO;
    }
}
