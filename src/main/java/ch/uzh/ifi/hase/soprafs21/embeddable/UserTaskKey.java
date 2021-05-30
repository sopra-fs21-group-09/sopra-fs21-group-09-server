package ch.uzh.ifi.hase.soprafs21.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserTaskKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "task_id")
    Long taskId;

    public UserTaskKey() {
    }

    public UserTaskKey(Long userId, Long taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserTaskKey that = (UserTaskKey) o;
//        return Objects.equals(userId, that.userId) &&
//                Objects.equals(taskId, that.taskId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userId, taskId);
//    }
}
