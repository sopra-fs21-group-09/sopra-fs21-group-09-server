package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Date;
import java.util.Set;

public class TaskPostDTO {

    private String name;
    private String description;
    private Set<TaskPostDTO> subTasks;
    private DeadlinePostDTO deadlinePostDTO;

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

    public DeadlinePostDTO getDeadlinePostDTO() {
        return deadlinePostDTO;
    }

    public void setDeadlinePostDTO(DeadlinePostDTO deadlinePostDTO) {
        this.deadlinePostDTO = deadlinePostDTO;
    }
}

