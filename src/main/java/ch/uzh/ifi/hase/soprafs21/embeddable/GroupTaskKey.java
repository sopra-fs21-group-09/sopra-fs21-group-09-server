package ch.uzh.ifi.hase.soprafs21.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GroupTaskKey implements Serializable {

    @Column(name = "group_id")
    Long groupId;

    @Column(name = "task_id")
    Long taskId;

    public GroupTaskKey() {
    }

    public GroupTaskKey(Long groupId, Long taskId) {
        this.groupId = groupId;
        this.taskId = taskId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
