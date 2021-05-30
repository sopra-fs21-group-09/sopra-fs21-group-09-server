package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.TaskRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // given
        testUser = new User();
        testUser.setId(1L);
        testUser.setPassword("testName");
        testUser.setUsername("testUsername");
        testUser.setToken(UUID.randomUUID().toString());
        testUser.setTasks(new HashSet<>());

        // when -> any object is being save in the userRepository -> return the dummy testUser
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(testUser);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testUser));
    }

    @Test
    public void createUser_validInputs_success() {
        // when -> any object is being save in the userRepository -> return the dummy testUser
        User createdUser = userService.createUser(testUser);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUser.getId(), createdUser.getId());
        assertEquals(testUser.getPassword(), createdUser.getPassword());
        assertEquals(testUser.getUsername(), createdUser.getUsername());
        assertNotNull(createdUser.getToken());
    }

    @Test
    public void getUserByID_validInputs_success(){
        User foundUser = userService.getUserById(testUser.getId());

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.any());

        assertEquals(foundUser.getId(), testUser.getId());
        assertEquals(foundUser.getUsername(), testUser.getUsername());
        assertNotNull(testUser.getToken());
    }

    @Test
    public void updateUser_validInputs_success() {
        // given
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);
        User updatesToUser = new User();
        updatesToUser.setPassword("updatedName");
        updatesToUser.setUsername("updatedUsernamee");

        userService.updateUser(1L, updatesToUser);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testUser.getPassword(), updatesToUser.getPassword());
        assertEquals(testUser.getUsername(), updatesToUser.getUsername());
    }

    @Test
    public void checkIfCredentialsWrong_validInputs_success(){
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(testUser);
        userService.checkIfCredentialsWrong(testUser);
    }

    @Test
    public void loginUser_validInputs_success() {

    }

    @Test
    public void addGroupToUser_validInputs_success(){}

    @Test
    public void addGroupToUser_alreadyInGroup_failure(){}

    @Test
    public void addGroupToUser_groupFull_failure(){}

    @Test
    public void createTaskForUser_validInputs_success(){
        Task testTask = new Task();
        testTask.setId(1L);
        testTask.setName("name");
        testTask.setDescription("description");

        Mockito.when(taskService.createTask(Mockito.any())).thenReturn(testTask);

        userService.createTaskForUser(1L, testTask);

        assertEquals(testUser.getTasks().iterator().next().getTask(), testTask);
    }
}