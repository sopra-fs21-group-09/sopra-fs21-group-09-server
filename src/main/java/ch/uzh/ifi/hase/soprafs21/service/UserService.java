package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.repository.TaskRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    private final TaskService taskService;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository,
                       ModuleService moduleService,
                       GroupService groupService,
                       TaskService taskService) {
        this.userRepository = userRepository;
        this.moduleService = moduleService;
        this.groupService = groupService;
        this.taskService = taskService;
    }

    public List<User> getUsers() {return this.userRepository.findAll();
    }

    public User createUser(User userInput) {
        userInput.setToken(UUID.randomUUID().toString());

        checkIfUserExists(userInput);

        userInput = userRepository.save(userInput);
        userRepository.flush();
        return userInput;
    }

    public void updateUser(Long id, User changesToUser){
        User userToBeUpdated = getUserById(id);
        if (changesToUser.getUsername() != userToBeUpdated.getUsername()){
            checkIfUserExists(changesToUser);
        }
        BeanUtils.copyProperties(changesToUser, userToBeUpdated, getNullPropertyNames(changesToUser));
        userRepository.save(userToBeUpdated);
        userRepository.flush();
    }

    public User loginUser(User user) {
        checkIfCredentialsWrong(user);

        User userByUsername = userRepository.findByUsername(user.getUsername());
        userByUsername.setToken(UUID.randomUUID().toString());

        userByUsername = userRepository.saveAndFlush(userByUsername);

        return userByUsername;
    }

    public void addModuleToUser(Long userId, Long moduleId) {
        User user = getUserById(userId);
        Module module = moduleService.getModuleById(moduleId);
        user.addModule(module);
    }

    public void addGroupToUser(Long userId, Long groupId) {
        User user = getUserById(userId);
        Group group = groupService.getGroupById(groupId);
        user.addGroup(group);
    }

    public Set<Module> getModulesFromUser(Long userId) {
        User user = getUserById(userId);
        return user.getModules();
    }

    public Set<Group> getGroupsFromUser(Long userId) {
        return getUserById(userId).getGroups();
    }

    public Set<Event> getEventsFromUser(Long userId) {
        User user = getUserById(userId);
        Set<Event> events = user.getEvents();

        Set<Module> modules = user.getModules();
        for (Module module: modules) {
            events.addAll(module.getEvents());
        }
        return user.getEvents();
    }

    public void createTaskForUser(Long id, Task newTask) {
        User user = getUserById(id);
        user.addTask(newTask);

        userRepository.saveAndFlush(user);
    }

    public Set<Task> getTasksFromUser(Long userId) {
        Set<Task> tasks = getUserById(userId).getTasks();

        Set<Module> modules = getModulesFromUser(userId);
        for (Module module : modules) {
            tasks.addAll(module.getTasks());
        }
        Set<Group> groups = getGroupsFromUser(userId);
        for (Group group : groups) {
            tasks.addAll(group.getTasks());
        }
        return  tasks;
    }

    public User getUserById(Long id){
        User user;
        if (userRepository.findById(id).isPresent()) {
            user = userRepository.findById(id).get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user was not found");
        }
        return user;
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
