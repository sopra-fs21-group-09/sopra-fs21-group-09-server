package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;

import java.util.List;

public class TaskGetDTO implements Comparable<TaskGetDTO>{
    private int id;
    private String name;
    private String description;
    private List<TaskGetDTO> subTasks;
    private DeadlineGetDTO deadline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public int compareTo(TaskGetDTO o) {
        return this.id - o.id;
    }
}
