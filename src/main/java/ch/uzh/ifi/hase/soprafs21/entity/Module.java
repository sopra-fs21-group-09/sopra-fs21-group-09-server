package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SequenceGenerator(name="moduleseq", initialValue=5, allocationSize=500)

@Entity
@Table(name = "MODULES")
public class Module implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="moduleseq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String prof_name;

    @Column
    private Long uzhId;

    @Column
    private String zoom_link;

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

    public String getZoom_link() {
        return zoom_link;
    }

    public void setZoom_link(String zoom_link) {
        this.zoom_link = zoom_link;
    }

    public Long getUzhId() {
        return uzhId;
    }

    public void setUzhId(Long uzhId) {
        this.uzhId = uzhId;
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
