package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.embeddable.UserTaskKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes the primary key
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String matrikelNr;

    @ManyToMany
    @JoinTable(
            name = "USERS_MODULES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID")
    )
    private Set<Module> modules = new HashSet<Module>();

    @ManyToMany
    @JoinTable(
            name = "USERS_GROUPS",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")
    )
    private Set<Group> groups = new HashSet<Group>();

    @OneToMany(mappedBy = "user")
    private Set<Event> events = new HashSet<Event>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserTask> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(String matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
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

    public void addModule(Module module) {
        this.modules.add(module);
        module.getUsers().add(this);
        for (var task : module.getTasks()) {
            this.addTask(task);
        }
    }

    public void removeModule(Module module) {
        this.modules.remove(module);
        module.getUsers().remove(this);
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        group.getMembers().add(this);
        group.setMemberCount(group.getMemberCount()+1);
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
        group.getMembers().remove(this);
        group.setMemberCount(group.getMemberCount()-1);
    }

    public void addEvent(Event event) {
        this.events.add(event);
        event.setUser(this);
    }

    public void removeEvent(Event event) {
        this.events.remove(event);
        event.setUser(this);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<UserTask> getTasks() {
        return tasks;
    }

    public void setTasks(Set<UserTask> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        var userTask = new UserTask();
        userTask.setId(new UserTaskKey());
        userTask.setUser(this);
        userTask.setTask(task);
        userTask.setCompleted(false);
        this.tasks.add(userTask);
    }
}
