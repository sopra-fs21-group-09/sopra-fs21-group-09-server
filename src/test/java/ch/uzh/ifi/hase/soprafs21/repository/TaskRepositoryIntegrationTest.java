package ch.uzh.ifi.hase.soprafs21.repository;


import ch.uzh.ifi.hase.soprafs21.entity.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureWebClient
public class TaskRepositoryIntegrationTest {

    private Task task;

    @Autowired
    private TestEntityManager entityManager;

    @Qualifier("taskRepository")
    @Autowired
    private TaskRepository taskRepository;

    @Qualifier("deadlineRepository")
    @Autowired
    private DeadlineRepository deadlineRepository;

    @BeforeEach
    public void setUp() {
        Deadline deadline = new Deadline();
        deadline.setVisible(false);
        deadline.setTime(LocalDateTime.parse("2021-01-01T12:00"));

        task = new Task();
        task.setName("name");
        task.setDescription("description");
        task.setDeadline(deadline);

        Task subTask = new Task();
        subTask.setName("sub_name");
        subTask.setDescription("sub_desc");
        subTask.setDeadline(new Deadline());
        subTask.getDeadline().setTime(LocalDateTime.parse("2021-01-01T10:00"));
        subTask.getDeadline().setVisible(false);
        task.setSubTasks(new ArrayList<>());
        task.addSubTask(subTask);

        entityManager.persist(task);
        entityManager.persist(subTask);
    }

    @Test
    public void findById_success() {
        // when
        Task found = taskRepository.findById(task.getId()).get();

        // then
        assertNotNull(found.getId());
        assertNotNull(found.getDeadline().getId());
        assertEquals(task.getName(), found.getName());
        assertEquals(task.getDescription(), found.getDescription());
        assertEquals(task.getId(), found.getDeadline().getId());
        assertEquals(task.getDeadline().getTime(), found.getDeadline().getTime());
        assertEquals(task.getDeadline().getVisible(), found.getDeadline().getVisible());
        assertEquals(task.getSubTasks(), found.getSubTasks());
        assertEquals(task.getSubTasks().get(0).getId(), found.getSubTasks().get(0).getId());
        assertEquals(task.getId(), found.getSubTasks().get(0).getParentTask().getId());
    }
}
