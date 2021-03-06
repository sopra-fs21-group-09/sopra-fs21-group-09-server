package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.embeddable.GroupTaskKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes the primary key
 */
@Entity
@Table(name = "GROUPS")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private Boolean open;

    @Column
    private String password;

    @Column
    private int memberLimit;

    @Column
    private int memberCount;

    @OneToOne
    private User creator;

    @ManyToMany(mappedBy = "groups")
    private Set<User> members;

    @ManyToOne
    @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID")
    private Module module;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupTask> tasks;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMemberLimit() {
        return memberLimit;
    }

    public void setMemberLimit(int memberLimit) {
        this.memberLimit = memberLimit;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public Set<User> getMembers() {
        return members;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void addCreator(User creator) {
        this.creator = creator;
    }

    public Set<GroupTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTask> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        var groupTask = new GroupTask();
        groupTask.setId(new GroupTaskKey());
        groupTask.setGroup(this);
        groupTask.setTask(task);
        groupTask.setCompleted(false);
        this.tasks.add(groupTask);
    }
}
