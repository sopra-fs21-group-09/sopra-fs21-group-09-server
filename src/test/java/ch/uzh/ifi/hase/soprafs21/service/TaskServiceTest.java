package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;
    private Deadline testDeadline;
    private Task testSubTaskA;
    private Task testSubTaskB;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testTask = new Task();
        testDeadline = new Deadline();
        testSubTaskA = new Task();
        testSubTaskB = new Task();

        testTask.setDeadline(testDeadline);
        testTask.setSubTasks(new ArrayList<Task>());
        testTask.addSubTask(testSubTaskA);
        testTask.addSubTask(testSubTaskB);

        testTask.setId(1L);
        testTask.setName("name");
        testTask.setDescription("description");

        testDeadline.setId(1L);
        testDeadline.setVisible(true);
        testDeadline.setTime(LocalDateTime.parse("2021-01-01T00:00:01"));

        testSubTaskA.setId(2L);
        testSubTaskA.setName("subA_name");

        testSubTaskB.setId(3L);
        testSubTaskB.setDescription("subB_desc");
    }

    @Test
    public void createTask_validInputs_success() {
        // when
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(testTask);
        Task createdTask = taskService.createTask(testTask);

        // then
        Mockito.verify(taskRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testTask.getId(), createdTask.getId());
        assertEquals(testTask.getName(), createdTask.getName());
        assertEquals(testTask.getDescription(), createdTask.getDescription());
        assertEquals(testDeadline.getId(), createdTask.getDeadline().getId());
        assertEquals(testTask.getSubTasks(), createdTask.getSubTasks());
    }

    @Test
    public void createSubTask_validInputs_success() {
        // when
        Mockito.when(taskRepository.save(Mockito.any())).thenReturn(testSubTaskA);
        Mockito.when(taskRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testTask));

        Task createdTask = taskService.createSubTask(testTask.getId(), testSubTaskA);

        // then
        Mockito.verify(taskRepository, Mockito.times(2)).findById(Mockito.any());
        Mockito.verify(taskRepository, Mockito.times(2)).save(Mockito.any());

        assertEquals(testTask.getId(), createdTask.getId());
        assertEquals(testTask.getName(), createdTask.getName());
        assertEquals(testTask.getSubTasks(), createdTask.getSubTasks());
        assertEquals(testSubTaskA.getId(), createdTask.getSubTasks().get(2).getId());
        assertEquals(testSubTaskA.getName(), createdTask.getSubTasks().get(2).getName());
    }


    @Test
    public void getTask_validInput_success() {
        // when
        Mockito.when(taskRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testTask));
        Task foundTask = taskService.getTask(testTask.getId());

        // then
        Mockito.verify(taskRepository, Mockito.times(2)).findById(Mockito.any());

        assertEquals(testTask.getId(), foundTask.getId());
        assertEquals(testTask.getName(), foundTask.getName());
        assertEquals(testTask.getDescription(), foundTask.getDescription());
        assertEquals(testDeadline.getId(), foundTask.getDeadline().getId());
        assertEquals(testTask.getSubTasks(), foundTask.getSubTasks());
    }

    @Test
    public void getTask_invalidInput_throwsException() {
        // when
        Mockito.when(taskRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        //then
        assertThrows(ResponseStatusException.class, () -> taskService.getTask(testTask.getId()));
    }

}
