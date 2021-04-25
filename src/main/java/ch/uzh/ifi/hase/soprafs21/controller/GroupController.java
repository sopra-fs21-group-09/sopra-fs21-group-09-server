package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupGetDTO;
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
    //TODO: add further mappings (get /groups/{groupID}, ...)

}