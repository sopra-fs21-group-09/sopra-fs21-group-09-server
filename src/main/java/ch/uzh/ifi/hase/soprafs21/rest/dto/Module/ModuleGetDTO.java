package ch.uzh.ifi.hase.soprafs21.rest.dto.Module;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskGetDTO;

import java.util.Comparator;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class ModuleGetDTO {
    private Long id;
    private String name;
    private String description;
    private String prof_name;
    private String zoom_link;
    private SortedSet<GroupGetDTO> groups;
    private HashSet<TaskGetDTO> tasks;

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

    public SortedSet<GroupGetDTO> getGroups() {
        return groups;
    }

    public void setGroups(SortedSet<GroupGetDTO> groups) {
        this.groups = groups;
    }

    public HashSet<TaskGetDTO> getTasks() {
        return tasks;
    }

    public void setTasks(HashSet<TaskGetDTO> tasks) {
        this.tasks = tasks;
    }
}
