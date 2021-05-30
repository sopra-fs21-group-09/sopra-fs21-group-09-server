package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.embeddable.GroupTaskKey;
import ch.uzh.ifi.hase.soprafs21.embeddable.UserTaskKey;
import ch.uzh.ifi.hase.soprafs21.entity.Group;
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
import static org.mockito.Mockito.doNothing;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskService taskService;

    @Mock
    private GroupService groupService;

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
        testUser.setGroups(new HashSet<>());

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
    public void addOpenGroupToUser_validInputs_success(){
        var user = new User();
        user.setId(2L);
        user.setPassword("Firstname Lastname");
        user.setUsername("creator");

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setDescription("description");
        testGroup.setCreator(user);
        testGroup.setOpen(true);
        testGroup.setMembers(new HashSet<>());
        testGroup.setMemberLimit(10);
        testGroup.setMemberCount(1);
        testGroup.setTasks(new HashSet<>());
        Mockito.when(groupService.getGroupById(Mockito.any())).thenReturn(testGroup);

        userService.addGroupToUser(testUser.getId(), testGroup.getId());

        assertEquals(testUser.getGroups().iterator().next(), testGroup);
    }

    @Test
    public void addGroupToUser_alreadyInGroup_failure(){
        var user = new User();
        user.setId(2L);
        user.setPassword("Firstname Lastname");
        user.setUsername("creator");

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setDescription("description");
        testGroup.setCreator(user);
        testGroup.setOpen(true);
        testGroup.setMembers(new HashSet<>());
        testGroup.setMemberLimit(10);
        testGroup.setMemberCount(10);
        testGroup.setTasks(new HashSet<>());
        testUser.addGroup(testGroup);
        Mockito.when(groupService.getGroupById(Mockito.any())).thenReturn(testGroup);

        assertThrows(ResponseStatusException.class, () -> userService.addGroupToUser(testUser.getId(), testGroup.getId()));
    }

    @Test
    public void addGroupToUser_groupFull_failure(){
        var user = new User();
        user.setId(2L);
        user.setPassword("Firstname Lastname");
        user.setUsername("creator");

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setDescription("description");
        testGroup.setCreator(user);
        testGroup.setOpen(true);
        testGroup.setMembers(new HashSet<>());
        testGroup.setMemberLimit(10);
        testGroup.setMemberCount(10);
        testGroup.setTasks(new HashSet<>());
        Mockito.when(groupService.getGroupById(Mockito.any())).thenReturn(testGroup);

        assertThrows(ResponseStatusException.class, () -> userService.addGroupToUser(testUser.getId(), testGroup.getId()));
    }

    @Test
    public void addPrivateGroupToUser_validInputs_success(){
        var user = new User();
        user.setId(2L);
        user.setPassword("Firstname Lastname");
        user.setUsername("creator");

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setDescription("description");
        testGroup.setCreator(user);
        testGroup.setOpen(false);
        testGroup.setPassword("password");
        testGroup.setMembers(new HashSet<>());
        testGroup.setMemberLimit(10);
        testGroup.setMemberCount(1);
        testGroup.setTasks(new HashSet<>());

        Group inputGroup = new Group();
        testGroup.setPassword("password");

        Mockito.when(groupService.getGroupById(Mockito.any())).thenReturn(testGroup);
        doNothing().when(groupService).checkPassword(Mockito.any(), Mockito.any());

        userService.addPrivateGroupToUser(testUser.getId(), testGroup.getId(), inputGroup);

        assertEquals(testUser.getGroups().iterator().next(), testGroup);
    }

    @Test
    public void createTaskForUser_validInputs_success(){
        Task testTask = new Task();
        testTask.setId(1L);
        testTask.setName("name");
        testTask.setDescription("description");

        Mockito.when(taskService.createTask(Mockito.any())).thenReturn(testTask);

        userService.createTaskForUser(1L, testTask);

        assertEquals(testUser.getTasks().iterator().next().getTask(), testTask);
        assertTrue(testUser.getTasks().iterator().next().getId()instanceof UserTaskKey);
    }
}