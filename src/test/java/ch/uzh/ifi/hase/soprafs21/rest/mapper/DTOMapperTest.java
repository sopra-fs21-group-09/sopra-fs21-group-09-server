package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.DeadlinePostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPutDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPutDTO;
import org.junit.jupiter.api.Test;

import javax.persistence.Temporal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */
public class DTOMapperTest {
    @Test
    public void testCreateUser_fromUserPostDTO_toUser_success() {
        // create UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("password");
        userPostDTO.setUsername("username");
        userPostDTO.setName("name");

        // MAP -> Create user
        User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // check content
        assertEquals(userPostDTO.getPassword(), user.getPassword());
        assertEquals(userPostDTO.getUsername(), user.getUsername());
        assertEquals(userPostDTO.getName(), user.getName());
    }

    @Test
    public void testGetUser_fromUser_toUserGetDTO_success() {
        // create User
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setToken("1");
        user.setMatrikelNr("00-000-000");

        // MAP -> Create UserGetDTO
        UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

        // check content
        assertEquals(user.getId(), userGetDTO.getId());
        assertEquals(user.getUsername(), userGetDTO.getUsername());
        assertEquals(user.getToken(), userGetDTO.getToken());
        assertEquals(user.getName(), userGetDTO.getName());
        assertEquals(user.getMatrikelNr(), userGetDTO.getMatrikelNr());
    }

    @Test
    public void testUpdateUser_fromUserPutDTO_toUser_success() {
        // create UserPutDTO
        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setName("name");
        userPutDTO.setUsername("username");
        userPutDTO.setMatrikelNr("00-000-000");

        // MAP -> Create User
        User user = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);

        // check content
        assertEquals(userPutDTO.getName(), user.getName());
        assertEquals(userPutDTO.getUsername(), user.getUsername());
        assertEquals(userPutDTO.getMatrikelNr(), user.getMatrikelNr());
        assertNull(user.getPassword());
        assertNull(user.getId());
        assertNull(user.getToken());
    }

    @Test
    public void testCreateTask_fromTaskPostDTO_toTask_success() {
        // create TaskPostDTO and DeadlinePostDTO
        DeadlinePostDTO deadlinePostDTO = new DeadlinePostDTO();
        deadlinePostDTO.setTime(LocalDateTime.parse("2021-01-01T00:00:00"));
        deadlinePostDTO.setVisible(true);

        TaskPostDTO taskPostDTO = new TaskPostDTO();
        taskPostDTO.setName("name");
        taskPostDTO.setDescription("description");
        taskPostDTO.setDeadline(deadlinePostDTO);

        // MAP -> Create Task
        Task task = DTOMapper.INSTANCE.convertTaskPostDTOtoEntity(taskPostDTO);

        // chek content
        assertEquals(taskPostDTO.getName(), task.getName());
        assertEquals(taskPostDTO.getDescription(), task.getDescription());
        assertEquals(taskPostDTO.getDeadline().getTime(), task.getDeadline().getTime());
        assertEquals(taskPostDTO.getDeadline().getVisible(), task.getDeadline().getVisible());
    }

    @Test
    public void testUpdateTask_fromTaskPutDTO_toTask_success() {
        // Create TaskPutDTO and DeadlinePostDTO
        DeadlinePostDTO deadlinePostDTO = new DeadlinePostDTO();
        deadlinePostDTO.setTime(LocalDateTime.parse("2021-01-01T00:00:00"));

        TaskPutDTO taskPutDTO = new TaskPutDTO();
        taskPutDTO.setName("name");
        taskPutDTO.setDeadline(deadlinePostDTO);

        // MAP -> Create Task
        Task task = DTOMapper.INSTANCE.convertTaskPutDTOtoEntity(taskPutDTO);

        // check content
        assertEquals(taskPutDTO.getName(), task.getName());
        assertEquals(taskPutDTO.getDeadline().getTime(), task.getDeadline().getTime());
        assertNull(task.getDescription());
        assertNull(task.getDeadline().getVisible());
        assertNull(task.getId());
        assertNull(task.getParentTask());
        assertNull(task.getSubTasks());
        assertNull(task.getUser());
        assertNull(task.getModule());
        assertNull(task.getGroup());
    }

    @Test
    public void testGetTask_fromTask_toTaskGetDTO_success() {
        // Create Task
        Task task = new Task();
        task.setName("name");
        task.setDescription("description");

        Deadline deadline = new Deadline();
        deadline.setTime(LocalDateTime.parse("2021-01-01T00:00:00"));
        deadline.setVisible(true);
        task.setDeadline(deadline);

        Task subTask = new Task();
        subTask.setName("sub_name");
        subTask.setDescription("sub_desc");
        task.setSubTasks(new ArrayList<>());
        task.addSubTask(subTask);

        // MAP -> Create TaskGetDTO
        TaskGetDTO taskGetDTO = DTOMapper.INSTANCE.convertEntityToTaskGetDTO(task);

        // check content
        assertEquals(task.getName(), taskGetDTO.getName());
        assertEquals(task.getDescription(), taskGetDTO.getDescription());
        assertEquals(task.getDeadline().getTime(), taskGetDTO.getDeadline().getTime());
        assertEquals(task.getDeadline().getVisible(), taskGetDTO.getDeadline().getVisible());
        assertEquals(subTask.getName(), taskGetDTO.getSubTasks().get(0).getName());
        assertEquals(subTask.getDescription(), taskGetDTO.getSubTasks().get(0).getDescription());
        assertNull(taskGetDTO.getSubTasks().get(0).getDeadline());
    }
}
