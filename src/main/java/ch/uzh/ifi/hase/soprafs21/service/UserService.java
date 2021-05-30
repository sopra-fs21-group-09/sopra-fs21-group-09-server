package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.repository.TaskRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.security.JWTUtility;
import ch.uzh.ifi.hase.soprafs21.security.SecurityUser;
import ch.uzh.ifi.hase.soprafs21.security.SecurityUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService extends AService{

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final ModuleService moduleService;
    private final GroupService groupService;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository,
                       ModuleService moduleService,
                       GroupService groupService,
                       @Qualifier("taskRepository") TaskRepository taskRepository,
                       TaskService taskService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       JWTUtility jwtUtility) {

        this.userRepository = userRepository;
        this.moduleService = moduleService;
        this.groupService = groupService;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtility = jwtUtility;
    }

    public List<User> getUsers() {return this.userRepository.findAll();
    }

    public User createUser(User userInput) {
        userInput.setToken(UUID.randomUUID().toString());
        userInput.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        checkIfUserExists(userInput);

        userInput = userRepository.save(userInput);
        userRepository.flush();
        return userInput;
    }

    public void updateUser(Long id, User changesToUser){
        User userToBeUpdated = getUserById(id);
        if (!changesToUser.getUsername().equals(userToBeUpdated.getUsername())){
            checkIfUserExists(changesToUser);
        }
        BeanUtils.copyProperties(changesToUser, userToBeUpdated, getNullPropertyNames(changesToUser));
        userRepository.save(userToBeUpdated);
        userRepository.flush();
    }

    public String loginUser(User user) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong credentials");
        }

        final UserDetails userDetails
                = userDetailsService.loadUserByUsername(user.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return token;
    }


    public void addModuleToUser(Long userId, Long moduleId) {
        User user = getUserById(userId);
        Module module = moduleService.getModuleById(moduleId);
        moduleService.loadModuleDetails(module);
        user.addModule(module);
    }

    public void removeModuleFromUser(Long userId, Long moduleId) {
        User user = getUserById(userId);
        Module module = moduleService.getModuleById(moduleId);
        user.removeModule(module);
    }

    public void addGroupToUser(Long userId, Long groupId) {
        User user = getUserById(userId);
        Group group = groupService.getGroupById(groupId);
        if(user.getGroups().contains(group)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You're already in this group. Why would you join again?");
        }
        if(group.getMemberCount() == group.getMemberLimit()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This group is already full");
        }
        user.addGroup(group);
    }

    public void addPrivateGroupToUser(Long userId, Long groupId, Group input) {
        User user = getUserById(userId);
        Group group = groupService.getGroupById(groupId);
        if(user.getGroups().contains(group)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You're already in this group. Why would you join again?");
        }
        groupService.checkPassword(group, input);
        user.addGroup(group);
    }

    public void removeGroupFromUser(Long userId, Long groupId) {
        User user = getUserById(userId);
        Group group = groupService.getGroupById(groupId);
        user.removeGroup(group);
        groupService.checkDelete(group);
    }

    public Set<Module> getModulesFromUser(Long userId) {
        User user = getUserById(userId);
        return user.getModules();
    }

    public Set<Group> getGroupsFromUser(Long userId) {
        User user = getUserById(userId);
        Set<Group> groups = user.getGroups();
        return groups;
    }

    public Set<Event> getEventsFromUser(Long userId) {
        User user = getUserById(userId);
        Set<Event> events = user.getEvents();

        Set<Module> modules = user.getModules();
        for (Module module: modules) {
            events.addAll(module.getEvents());
        }
        return events;
    }

    public void createTaskForUser(Long id, Task newTask) {
        User user = getUserById(id);
        var taskToAdd = taskService.createTask(newTask);
        user.addTask(taskToAdd);

        userRepository.saveAndFlush(user);
    }

    public Set<Task> getTasksFromUser(Long userId) {
        var tasks = taskRepository.findAllTasksForUserByUserId(userId);
        tasks.addAll(taskRepository.findAllGroupTasksForUserByUserId(userId));

        return tasks;
    }

    public Set<Task> getTasksWithDeadlineFromUser(Long userId) {
        var deadlines = taskRepository.findAllTasksWithDeadlineForUserByUserId(userId);
        deadlines.addAll(taskRepository.findAllGroupTasksWithDeadlineForGroupByUserId(userId));

        return deadlines;
    }

    public Set<Task> getClosedTasksFromUser(Long userId) {
        var tasks = taskRepository.findAllClosedTasksForUserByUserId(userId);
        tasks.addAll(taskRepository.findAllClosedGroupTasksForUserByUserId(userId));

        return tasks;
    }

    public Set<Task> getOpenTasksFromUser(Long userId) {
        var tasks = taskRepository.findAllOpenTasksForUserByUserId(userId);
        tasks.addAll(taskRepository.findAllOpenGroupTasksForUserByUserId(userId));

        return tasks;
    }

    public void toggle(Long userId, Long taskId) {
        taskRepository.toggleUserTaskCompleted(userId, taskId);
    }

    public User getUserById(Long id){
        Optional<User> checkUser = userRepository.findById(id);
        if (checkUser.isPresent()) {
            return checkUser.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not found");
        }
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    private void checkIfUserExists(User user) {
        User userByUsername = userRepository.findByUsername(user.getUsername());

        if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This username already exists. Choose another one.");
        }
    }

    private void checkIfCredentialsWrong(User userToBeLoggedIn) {
        User userByUsername = userRepository.findByUsername(userToBeLoggedIn.getUsername());

        if (userByUsername == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password are incorrect.");
        }
        else if (!userByUsername.getPassword().equals(userToBeLoggedIn.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password incorrect.");
        }
    }

}
