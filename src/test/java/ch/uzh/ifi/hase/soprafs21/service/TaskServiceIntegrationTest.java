package ch.uzh.ifi.hase.soprafs21.service;


import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.TaskRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class TaskServiceIntegrationTest {

    @Qualifier("taskRepository")
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        taskRepository.deleteAll();
    }


    @Test
    public void createSubTask_validInputs_success() {
        // given
        Task testTask = new Task();
        testTask.setId(1L);
        testTask.setName("name");
        testTask.setDescription("description");
        taskRepository.save(testTask);
        taskRepository.flush();

        Task testSubTaskA = new Task();
        testSubTaskA.setId(2L);
        testSubTaskA.setName("subA_name");
        testSubTaskA.setDescription("description 2");

        // when
        taskService.createSubTask(1L, testSubTaskA);

        // then
        assertNotNull(taskRepository.findById(2L));
        assertEquals(taskRepository.findById(2L).get().getParentTask().getId(), testTask.getId());
        assertEquals(taskRepository.findById(2L).get().getName(), testSubTaskA.getName());
        assertEquals(taskRepository.findById(2L).get().getDescription(), testSubTaskA.getDescription());
    }

    @Test
    public void createSubTask_invalidInputs_throwsException() {
        // given
        Task testSubTaskA = new Task();
        testSubTaskA.setId(2L);
        testSubTaskA.setName("subA_name");

        // when then
        assertThrows(ResponseStatusException.class, () -> taskService.createSubTask(1L, testSubTaskA));
    }

}