package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.DeadlinePostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPutDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPutDTO;
import org.junit.jupiter.api.Test;

import javax.persistence.Temporal;

import java.time.LocalDate;
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
        deadlinePostDTO.setTime("2021-01-01");
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
        assertEquals(taskPostDTO.getDeadline().getTime(), task.getDeadline().getTime().toLocalDate().toString());
        assertEquals(taskPostDTO.getDeadline().getVisible(), task.getDeadline().getVisible());
    }

    @Test
    public void testUpdateTask_fromTaskPutDTO_toTask_success() {
        // Create TaskPutDTO and DeadlinePostDTO
        DeadlinePostDTO deadlinePostDTO = new DeadlinePostDTO();
        deadlinePostDTO.setTime("2021-01-01");

        TaskPutDTO taskPutDTO = new TaskPutDTO();
        taskPutDTO.setName("name");
        taskPutDTO.setDeadline(deadlinePostDTO);

        // MAP -> Create Task
        Task task = DTOMapper.INSTANCE.convertTaskPutDTOtoEntity(taskPutDTO);

        // check content
        assertEquals(taskPutDTO.getName(), task.getName());
        assertEquals(taskPutDTO.getDeadline().getTime(), task.getDeadline().getTime().toLocalDate().toString());
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

    @Test
    public void testCreateGroup_fromGroupPostDTO_toGroup_success() {
        // Create GroupPostDTO
        GroupPostDTO groupPostDTO = new GroupPostDTO();
        groupPostDTO.setName("name");
        groupPostDTO.setDescription("description");
        groupPostDTO.setOpen(false);
        groupPostDTO.setPassword("password");
        groupPostDTO.setMemberLimit(10);

        // MAP -> create group
        Group group =DTOMapper.INSTANCE.convertGroupPostDTOtoEntity(groupPostDTO);

        // check content
        assertEquals(groupPostDTO.getName(), group.getName());
        assertEquals(groupPostDTO.getDescription(), group.getDescription());
        assertEquals(groupPostDTO.getOpen(), group.getOpen());
        assertEquals(groupPostDTO.getPassword(), group.getPassword());
        assertEquals(groupPostDTO.getMemberLimit(), group.getMemberLimit());
        assertNull(group.getId());
        assertNull(group.getCreator());
        assertEquals(0, group.getMemberCount());
//        assertNull(group.getMembers());
        assertNull(group.getModule());
//        assertNull(group.getTasks());
    }

    @Test
    public void testGetGroup_fromGroup_toGroupGetDTO_success() {
        // Create Group
        Group group = new Group();
        group.setId(1L);
        group.setName("name");
        group.setOpen(true);
        group.setMemberCount(5);
        group.setMemberLimit(10);
        group.setCreator(new User());
        group.getCreator().setId(1L);
        group.getCreator().setUsername("username");

        // MAP -> create GroupGetDTO
        GroupGetDTO groupGetDTO = DTOMapper.INSTANCE.convertEntityToGroupGetDTO(group);

        // check content
        assertEquals(group.getId(), groupGetDTO.getId());
        assertEquals(group.getName(), groupGetDTO.getName());
        assertEquals(group.getOpen(), groupGetDTO.getOpen());
        assertEquals(group.getMemberCount(), groupGetDTO.getMemberCount());
        assertEquals(group.getMemberLimit(), groupGetDTO.getMemberLimit());
        assertEquals(group.getCreator().getId(), groupGetDTO.getCreator().getId());
        assertEquals(groupGetDTO.getCreator().getUsername(), groupGetDTO.getCreator().getUsername());
    }
}
