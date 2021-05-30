package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.embeddable.GroupTaskKey;
import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private TaskService taskService;


    @InjectMocks
    private GroupService groupService;

    private Group testGroup;

    @BeforeEach
    public void SetUp() {
        MockitoAnnotations.openMocks(this);

        var user = new User();
        user.setId(1L);
        user.setPassword("Firstname Lastname");
        user.setUsername("creator");

        testGroup = new Group();
        testGroup.setId(1L);
        testGroup.setName("group");
        testGroup.setDescription("description");
        testGroup.setCreator(user);
        testGroup.setOpen(false);
        testGroup.setPassword("password");
        testGroup.setMemberLimit(10);
        testGroup.setMemberCount(1);
        testGroup.setTasks(new HashSet<>());

        Mockito.when(groupRepository.save(Mockito.any())).thenReturn(testGroup);
        Mockito.when(groupRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testGroup));
    }

    @Test
    public void getGroup_validInput_success() {
        // when
        Mockito.when(groupRepository.findById(Mockito.any())).thenThrow(ResponseStatusException.class);

        //then
        assertThrows(ResponseStatusException.class, () -> groupService.getGroupById(testGroup.getId()));
    }

    @Test
    public void getGroup_invalidInput_throwsException() {
        // when
        Mockito.when(groupRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        //then
        assertThrows(ResponseStatusException.class, () -> groupService.getGroupById(testGroup.getId()));
    }

    @Test
    public void createTaskForGroup_validInputs_success(){
        Task testTask = new Task();
        testTask.setId(1L);
        testTask.setName("name");
        testTask.setDescription("description");

        Mockito.when(taskService.createTask(Mockito.any())).thenReturn(testTask);

        groupService.createTaskForGroup(1L, testTask);

        assertEquals(testGroup.getTasks().iterator().next().getTask(), testTask);
        assertTrue(testGroup.getTasks().iterator().next().getId()instanceof GroupTaskKey);
    }
}
