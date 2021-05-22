package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController {

    private final GroupService groupService;

    GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GroupGetDTO> getAllGroups() {
        List<Group> groups = groupService.getGroups();
        List<GroupGetDTO> groupGetDTOs = new ArrayList<>();

        for (Group group : groups) {
            groupGetDTOs.add(DTOMapper.INSTANCE.convertEntityToGroupGetDTO(group));
        }
        return groupGetDTOs;
    }

    @GetMapping("/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GroupGetDTO getGroupById(@PathVariable Long groupId) {
        var group = groupService.getGroupById(groupId);

        return DTOMapper.INSTANCE.convertEntityToGroupGetDTO(group);
    }

    @PostMapping("/users/{userId}/groups")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void userCreateGroup(@RequestBody GroupPostDTO groupPostDTO, @PathVariable Long userId){
        Group input = DTOMapper.INSTANCE.convertGroupPostDTOtoEntity(groupPostDTO);

        groupService.createGroupForUser(input, userId);
    }

    @PostMapping("/modules/{moduleId}/users/{userId}/groups")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void userCreateGroupInModule(@RequestBody GroupPostDTO groupPostDTO, @PathVariable Long userId, @PathVariable Long moduleId){
        Group input = DTOMapper.INSTANCE.convertGroupPostDTOtoEntity(groupPostDTO);

        groupService.createGroupForModule(input, userId, moduleId);
    }

    @PostMapping("/groups/{groupId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createTaskForGroup(@PathVariable Long groupId, @RequestBody TaskPostDTO taskPostDTO) {
        var input = DTOMapper.INSTANCE.convertTaskPostDTOtoEntity(taskPostDTO);

        groupService.createTaskForGroup(groupId, input);
    }

    @GetMapping("/groups/{groupId}/tasks")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<TaskGetDTO> getTasksFromGroup(@PathVariable Long groupId) {
        var tasks = groupService.getTasksFromGroup(groupId);

        List<TaskGetDTO> taskGetDTOs= new ArrayList<>();

        for (Task task: tasks) {
            taskGetDTOs.add(DTOMapper.INSTANCE.convertEntityToTaskGetDTO(task));
        }
        return taskGetDTOs;
    }
}