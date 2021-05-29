package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Module.ModuleGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.CustomDeadlineGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.DeadlineGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPutDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to the user.
 * The controller will receive the request and delegate the execution to the UserService and finally return the result.
 */


@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO loginUser(@RequestBody UserPostDTO userPostDTO) {
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        User loggedInUser = userService.loginUser(userInput);

        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(loggedInUser);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserGetDTO createUser(@RequestBody UserPostDTO userPostDTO) {
        User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        User createdUser = userService.createUser(userInput);

        return DTOMapper.INSTANCE.convertEntityToUserGetDTO(createdUser);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<UserGetDTO> getAllUsers() {
        List<User> users = userService.getUsers();
        List<UserGetDTO> userGetDTOs = new ArrayList<>();

        for (User user : users) {
            userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
        }
        return userGetDTOs;
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO getUserInformation(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        UserGetDTO userDTO= DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);
        return userDTO;
    }

    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void edit(@PathVariable Long userId, @RequestBody UserPutDTO UserPutDTO) {
        User input = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(UserPutDTO);

        userService.updateUser(userId, input);
    }

    @PostMapping("/users/{userId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void joinModule(@PathVariable Long userId, @PathVariable Long moduleId) {
        userService.addModuleToUser(userId, moduleId);
    }

    @DeleteMapping("/users/{userId}/modules/{moduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void leaveModule(@PathVariable Long userId, @PathVariable Long moduleId) {
        userService.removeModuleFromUser(userId, moduleId);
    }

    @PostMapping("/users/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void joinPublicGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.addGroupToUser(userId, groupId);
    }

    @PostMapping("/users/{userId}/groups/{groupId}/private")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void joinPrivateGroup(@PathVariable Long userId, @PathVariable Long groupId, @RequestBody GroupPostDTO groupPostDTO) {
        Group input = DTOMapper.INSTANCE.convertGroupPostDTOtoEntity(groupPostDTO);
        userService.addPrivateGroupToUser(userId, groupId, input);
    }

    @DeleteMapping("/users/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void leaveGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.removeGroupFromUser(userId, groupId);
    }

    @GetMapping("/users/{userId}/modules")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ModuleGetDTO> getModulesFromUser(@PathVariable Long userId) {
        Set<Module> modules = userService.getModulesFromUser(userId);
        List<ModuleGetDTO> moduleGetDTOs = new ArrayList<>();

        for (Module module : modules) {
            moduleGetDTOs.add(DTOMapper.INSTANCE.convertEntityToModuleGetDTO(module));
        }
        return moduleGetDTOs;
    }

    @GetMapping("/users/{userId}/groups")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GroupGetDTO> getGroupsFromUser(@PathVariable Long userId) {
        Set<Group> groups = userService.getGroupsFromUser(userId);
        List<GroupGetDTO> groupGetDTOs = new ArrayList<>();

        for (Group group: groups) {
            groupGetDTOs.add(DTOMapper.INSTANCE.convertEntityToGroupGetDTO(group));
        }
        return groupGetDTOs;
    }

    @GetMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SortedSet<EventGetDTO> getEventsFromUser(@PathVariable Long userId) {
        Set<Event> events = userService.getEventsFromUser(userId);

        SortedSet<EventGetDTO> eventGetDTOs = new TreeSet<>();

        for (Event event: events) {
            eventGetDTOs.add(DTOMapper.INSTANCE.convertEntityToEventGetDTO(event));
        }
        return eventGetDTOs;
    }

    @PostMapping("/users/{userId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createTaskForUser(@PathVariable Long userId, @RequestBody TaskPostDTO taskPostDTO) {
        Task input = DTOMapper.INSTANCE.convertTaskPostDTOtoEntity(taskPostDTO);

        userService.createTaskForUser(userId, input);
    }

    @GetMapping("/users/{userId}/tasks")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public SortedSet<TaskGetDTO> getTasksFromUser(@PathVariable Long userId, @RequestParam(required = false) Boolean completed) {
        Set<Task> tasks;

        if (completed == null) {
            tasks = userService.getTasksFromUser(userId);
        }
        else {
            if (completed) {
                tasks = userService.getClosedTasksFromUser(userId);
            }
            else {
                tasks = userService.getOpenTasksFromUser(userId);
            }
        }

        SortedSet<TaskGetDTO> taskGetDTOs= new TreeSet<>();

        for (Task task: tasks) {
            taskGetDTOs.add(DTOMapper.INSTANCE.convertEntityToTaskGetDTO(task));
        }
        return taskGetDTOs;
    }

    @GetMapping("/users/{userId}/deadlines")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CustomDeadlineGetDTO> getDeadlinesFromUser(@PathVariable Long userId){
        Set<Task> deadlines = userService.getTasksWithDeadlineFromUser(userId);

        List<CustomDeadlineGetDTO> CustomDeadlineGetDTOs= new ArrayList<>();

        for (Task deadline: deadlines) {
            CustomDeadlineGetDTOs.add(DTOMapper.INSTANCE.convertEntityToCustomDeadlineGetDTO(deadline));
        }
        return CustomDeadlineGetDTOs;
    }

    @PatchMapping("/users/{userId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void toggle(@PathVariable Long userId, @PathVariable Long taskId) {

        userService.toggle(userId, taskId);
    }
}