package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MODULES")
public class Module implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition="LONGTEXT")
    private String description;

    @Column
    private String prof_name;

    @Column
    private String category;

    @Column
    private String lectureTimeStart;

    @Column
    private String lectureTimeEnd;

    @ManyToMany(mappedBy = "modules")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "module")
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "module")
    private Set<Group> groups = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

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

    public String getProf_name() {
        return prof_name;
    }

    public void setProf_name(String prof_name) {
        this.prof_name = prof_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLectureTimeStart() {
        return lectureTimeStart;
    }

    public void setLectureTimeStart(String lectureTimeStart) {
        this.lectureTimeStart = lectureTimeStart;
    }

    public String getLectureTimeEnd() {
        return lectureTimeEnd;
    }

    public void setLectureTimeEnd(String lectureTimeEnd) {
        this.lectureTimeEnd = lectureTimeEnd;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void addEvent(Event event) {
        this.events.add(event);
        event.setModule(this);
    }
    public void addGroup(Group group) {
        this.groups.add(group);
        group.setModule(this);
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
        group.setModule(null);
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        for (var user : this.users) {
            user.addTask(task);
        }
    }
}
