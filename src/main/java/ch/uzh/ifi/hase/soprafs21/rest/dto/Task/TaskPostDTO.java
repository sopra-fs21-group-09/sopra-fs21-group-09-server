package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;

public class TaskPostDTO {
    private String name;
    private String description;
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

    public DeadlinePostDTO getDeadline() {
        return deadline;
    }

    public void setDeadline(DeadlinePostDTO deadline) {
        this.deadline = deadline;
    }
}

