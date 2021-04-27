package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
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

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository, ModuleService moduleService) {
        this.userRepository = userRepository;
        this.moduleService = moduleService;
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

    public Set<Module> getModulesFromUser(Long userId) {
        User user = getUserById(userId);
        return user.getModules();
    }

    public Set<Event> getEventsFromUser(Long userId) {
        User user = getUserById(userId);
        return user.getEvents();
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
