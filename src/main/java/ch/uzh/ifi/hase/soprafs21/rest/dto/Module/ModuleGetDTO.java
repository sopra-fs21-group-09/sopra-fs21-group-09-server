package ch.uzh.ifi.hase.soprafs21.rest.dto.Module;

import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskGetDTO;

import java.util.HashSet;
import java.util.SortedSet;

public class ModuleGetDTO {
    private Long id;
    private String name;
    private String description;
    private String prof_name;
    private String category;
    private String lectureTimeStart;
    private String lectureTimeEnd;
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
