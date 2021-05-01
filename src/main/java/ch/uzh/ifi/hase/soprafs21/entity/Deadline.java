package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "DEADLINES")
public class Deadline implements Serializable {
    //TODO: BUG!!! "visible":null in json is mapped to visible = false and updates deadlines unwillingly!!!

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "task_id")
    private Long id;

    private LocalDateTime time;

    private Boolean visible;

    @OneToOne
    @MapsId
    @JoinColumn(name = "task_id")
    private Task task;

    public Deadline() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
