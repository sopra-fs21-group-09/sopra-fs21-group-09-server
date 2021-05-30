package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.embeddable.UserTaskKey;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
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
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskService taskService;

    @Mock
    private GroupService groupService;

    @Mock
    private ModuleService moduleService;

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
        testUser.setModules(new HashSet<>());
        testUser.setEvents(new HashSet<>());

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

    @Test
    public void removeGroupFromUser_validInputs_success(){
        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setMembers(new HashSet<>());

        testUser.addGroup(testGroup);

        assertFalse(testUser.getGroups().isEmpty());

        Mockito.when(groupService.getGroupById(Mockito.any())).thenReturn(testGroup);

        userService.removeGroupFromUser(testUser.getId(), testGroup.getId());

        assertTrue(testUser.getGroups().isEmpty());
    }

    @Test
    public void removeModuleFromUser_validInputs_success(){
        Module testModule = new Module();
        testModule.setId(1L);

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setMembers(new HashSet<>());

        testModule.addGroup(testGroup);
        testUser.addModule(testModule);
        testUser.addGroup(testGroup);

        assertFalse(testUser.getModules().isEmpty());
        assertFalse(testUser.getGroups().isEmpty());

        Mockito.when(groupService.getGroupById(Mockito.any())).thenReturn(testGroup);
        Mockito.when(moduleService.getModuleById(Mockito.any())).thenReturn(testModule);

        userService.removeModuleFromUser(testUser.getId(), testModule.getId());

        assertTrue(testUser.getGroups().isEmpty());
        assertTrue(testUser.getModules().isEmpty());
    }

    @Test
    public void getEventsFromUser_validInputs_success(){
        Module testModule = new Module();
        testModule.setId(1L);

        Event testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setName("ModuleEvent");

        Event testEvent2 = new Event();
        testEvent2.setId(1L);
        testEvent2.setName("UserEvent");

        testModule.addEvent(testEvent);
        testUser.addModule(testModule);

        testUser.addEvent(testEvent2);

        Set<Event> events = userService.getEventsFromUser(testUser.getId());
        assertTrue(events.contains(testEvent));
        assertTrue(events.contains(testEvent2));
    }


    @Test
    public void getTasksFromUser_validInputs_success(){
        Module testModule = new Module();
        testModule.setId(1L);

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setTasks(new HashSet<>());
        testGroup.setMembers(new HashSet<>());

        Task testTask1 = new Task();
        testTask1.setId(1L);
        testTask1.setName("name");
        testTask1.setDescription("description");

        Task testTask2 = new Task();
        testTask2.setId(2L);
        testTask2.setName("name");
        testTask2.setDescription("description");

        Task testTask3 = new Task();
        testTask3.setId(2L);
        testTask3.setName("name");
        testTask3.setDescription("description");

        testModule.addTask(testTask1);
        testUser.addModule(testModule);

        testGroup.addTask(testTask2);
        testUser.addGroup(testGroup);

        testUser.addTask(testTask3);

        Set<Task> userTasks = new HashSet<>();
        userTasks.add(testTask1);
        userTasks.add(testTask2);

        Set<Task> groupTasks = new HashSet<>();
        groupTasks.add(testTask3);

        Mockito.when(taskRepository.findAllTasksForUserByUserId(Mockito.any())).thenReturn(userTasks);
        Mockito.when(taskRepository.findAllGroupTasksForUserByUserId(Mockito.any())).thenReturn(groupTasks);

        Set<Task> tasks = userService.getTasksFromUser(testUser.getId());
        assertTrue(tasks.contains(testTask1));
        assertTrue(tasks.contains(testTask2));
        assertTrue(tasks.contains(testTask3));
    }

    @Test
    public void getTasksWithDeadlineFromUser_validInputs_success(){
        Module testModule = new Module();
        testModule.setId(1L);

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setTasks(new HashSet<>());
        testGroup.setMembers(new HashSet<>());

        Deadline testDeadline = new Deadline();

        Task testTask1 = new Task();
        testTask1.setDeadline(testDeadline);
        testTask1.setId(1L);
        testTask1.setName("name");
        testTask1.setDescription("description");

        Task testTask2 = new Task();
        testTask2.setDeadline(testDeadline);
        testTask2.setId(2L);
        testTask2.setName("name");
        testTask2.setDescription("description");

        Task testTask3 = new Task();
        testTask3.setDeadline(testDeadline);
        testTask3.setId(2L);
        testTask3.setName("name");
        testTask3.setDescription("description");

        testModule.addTask(testTask1);
        testUser.addModule(testModule);

        testGroup.addTask(testTask2);
        testUser.addGroup(testGroup);

        testUser.addTask(testTask3);

        Set<Task> userTasks = new HashSet<>();
        userTasks.add(testTask1);
        userTasks.add(testTask2);

        Set<Task> groupTasks = new HashSet<>();
        groupTasks.add(testTask3);

        Mockito.when(taskRepository.findAllTasksWithDeadlineForUserByUserId(Mockito.any())).thenReturn(userTasks);
        Mockito.when(taskRepository.findAllGroupTasksWithDeadlineForGroupByUserId(Mockito.any())).thenReturn(groupTasks);

        Set<Task> tasks = userService.getTasksWithDeadlineFromUser(testUser.getId());
        assertTrue(tasks.contains(testTask1));
        assertTrue(tasks.contains(testTask2));
        assertTrue(tasks.contains(testTask3));
    }

    @Test
    public void getClosedTasksFromUser_validInputs_success(){
        Module testModule = new Module();
        testModule.setId(1L);

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setTasks(new HashSet<>());
        testGroup.setMembers(new HashSet<>());

        Deadline testDeadline = new Deadline();

        Task testTask1 = new Task();
        testTask1.setDeadline(testDeadline);
        testTask1.setId(1L);
        testTask1.setName("name");
        testTask1.setDescription("description");

        Task testTask2 = new Task();
        testTask2.setDeadline(testDeadline);
        testTask2.setId(2L);
        testTask2.setName("name");
        testTask2.setDescription("description");

        Task testTask3 = new Task();
        testTask3.setDeadline(testDeadline);
        testTask3.setId(2L);
        testTask3.setName("name");
        testTask3.setDescription("description");

        testModule.addTask(testTask1);
        testUser.addModule(testModule);

        testGroup.addTask(testTask2);
        testUser.addGroup(testGroup);

        testUser.addTask(testTask3);

        Set<Task> userTasks = new HashSet<>();
        userTasks.add(testTask1);
        userTasks.add(testTask2);

        Set<Task> groupTasks = new HashSet<>();
        groupTasks.add(testTask3);

        userService.toggle(testUser.getId(), testTask1.getId());
        userService.toggle(testUser.getId(), testTask2.getId());
        userService.toggle(testUser.getId(), testTask3.getId());

        doNothing().when(taskRepository).toggleUserTaskCompleted(Mockito.any(), Mockito.any());

        Mockito.when(taskRepository.findAllClosedTasksForUserByUserId(Mockito.any())).thenReturn(userTasks);
        Mockito.when(taskRepository.findAllClosedGroupTasksForUserByUserId(Mockito.any())).thenReturn(groupTasks);

        Set<Task> tasks = userService.getClosedTasksFromUser(testUser.getId());
        assertTrue(tasks.contains(testTask1));
        assertTrue(tasks.contains(testTask2));
        assertTrue(tasks.contains(testTask3));
    }

    @Test
    public void getOpenTasksFromUser_validInputs_success(){
        Module testModule = new Module();
        testModule.setId(1L);

        Group testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setTasks(new HashSet<>());
        testGroup.setMembers(new HashSet<>());

        Deadline testDeadline = new Deadline();

        Task testTask1 = new Task();
        testTask1.setDeadline(testDeadline);
        testTask1.setId(1L);
        testTask1.setName("name");
        testTask1.setDescription("description");

        Task testTask2 = new Task();
        testTask2.setDeadline(testDeadline);
        testTask2.setId(2L);
        testTask2.setName("name");
        testTask2.setDescription("description");

        Task testTask3 = new Task();
        testTask3.setDeadline(testDeadline);
        testTask3.setId(2L);
        testTask3.setName("name");
        testTask3.setDescription("description");

        testModule.addTask(testTask1);
        testUser.addModule(testModule);

        testGroup.addTask(testTask2);
        testUser.addGroup(testGroup);

        testUser.addTask(testTask3);

        Set<Task> userTasks = new HashSet<>();
        userTasks.add(testTask1);
        userTasks.add(testTask2);

        Set<Task> groupTasks = new HashSet<>();
        groupTasks.add(testTask3);

        Mockito.when(taskRepository.findAllOpenTasksForUserByUserId(Mockito.any())).thenReturn(userTasks);
        Mockito.when(taskRepository.findAllOpenGroupTasksForUserByUserId(Mockito.any())).thenReturn(groupTasks);

        Set<Task> tasks = userService.getOpenTasksFromUser(testUser.getId());
        assertTrue(tasks.contains(testTask1));
        assertTrue(tasks.contains(testTask2));
        assertTrue(tasks.contains(testTask3));
    }


}