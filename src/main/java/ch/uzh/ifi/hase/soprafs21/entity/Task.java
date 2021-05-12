package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "TASKS")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask")
    private List<Task> subTasks;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Deadline deadline;

    @OneToMany(mappedBy = "task")
    Set<UserTask> users;

//    @ManyToOne
//    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")
//    private Group group;

    @OneToMany(mappedBy = "task")
    Set<GroupTask> groups;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
        if (this.deadline != null) this.deadline.setTask(this);
    }

    public void addSubTask(Task subTask) {
        this.subTasks.add(subTask);
        subTask.setParentTask(this);
    }

    public Set<UserTask> getUsers() {
        return users;
    }

    public void setUsers(Set<UserTask> users) {
        this.users = users;
    }

//    public Group getGroup() {
//        return group;
//    }
//
//    public void setGroup(Group group) {
//        this.group = group;
//    }


    public Set<GroupTask> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupTask> groups) {
        this.groups = groups;
    }
}
