package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.embeddable.UserTaskKey;

import javax.persistence.*;

@Entity
@Table(name = "USERS_TASKS")
public class UserTask {

    @EmbeddedId
    UserTaskKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    Task task;

    Boolean completed;

    public UserTaskKey getId() {
        return id;
    }

    public void setId(UserTaskKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
