package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventPutDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Module.ModuleGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.DeadlinePostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPutDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPutDTO;
import org.junit.jupiter.api.Test;

import javax.persistence.Temporal;

import java.time.Instant;
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
        Group group = DTOMapper.INSTANCE.convertGroupPostDTOtoEntity(groupPostDTO);

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

    @Test
    public void testCreateEvent_fromEventPostDTO_toEvent_success() {
        // create EventPostDTO
        EventPostDTO eventPostDTO = new EventPostDTO();
        eventPostDTO.setTitle("title");
        eventPostDTO.setDesc("desc");
        eventPostDTO.setStart(Date.from(Instant.parse("2021-01-01T10:00:00+00:00")));
        eventPostDTO.setEnd(Date.from(Instant.parse("2021-01-01T10:00:00+00:00")));
        eventPostDTO.setLabel(EventLabel.LECTURE);

        // MAP -> create Event
        Event event = DTOMapper.INSTANCE.convertEventPostDTOtoEntity(eventPostDTO);

        // check content
        assertEquals(eventPostDTO.getTitle(), event.getName());
        assertEquals(eventPostDTO.getDesc(), event.getDescription());
        assertEquals(eventPostDTO.getStart(), event.getStartTime());
        assertEquals(eventPostDTO.getEnd(), event.getEndTime());
        assertEquals(eventPostDTO.getLabel(), event.getLabel());
    }

    @Test
    public void testUpdateEvent_fromEventPutDTO_toEvent_success() {
        // create EventPutDTO
        EventPutDTO eventPutDTO = new EventPutDTO();
        eventPutDTO.setTitle("title");
        eventPutDTO.setDesc("desc");
        eventPutDTO.setStart(Date.from(Instant.parse("2021-01-01T10:00:00+00:00")));
        eventPutDTO.setEnd(Date.from(Instant.parse("2021-01-01T10:00:00+00:00")));
        eventPutDTO.setLabel(EventLabel.LECTURE);
        eventPutDTO.setTaskId(1L);

        // MAP -> crate Event
        Event event = DTOMapper.INSTANCE.convertEventPutDTOtoEntity(eventPutDTO);

        // check content
        assertEquals(eventPutDTO.getTitle(), event.getName());
        assertEquals(eventPutDTO.getDesc(), event.getDescription());
        assertEquals(eventPutDTO.getStart(), event.getStartTime());
        assertEquals(eventPutDTO.getEnd(), event.getEndTime());
        assertEquals(eventPutDTO.getLabel(), event.getLabel());
    }

    @Test
    public void testGetEvent_fromEvent_toEventGetDTO_success() {
        // create Event
        Event event = new Event();
        event.setId(1L);
        event.setName("name");
        event.setDescription("desc");
        event.setStartTime(Date.from(Instant.parse("2021-01-01T10:00:00+00:00")));
        event.setEndTime(Date.from(Instant.parse("2021-01-01T10:00:00+00:00")));
        event.setLabel(EventLabel.LECTURE);

        // MAP -> create EventGetDTO
        EventGetDTO eventGetDTO = DTOMapper.INSTANCE.convertEntityToEventGetDTO(event);

        // check content
        assertEquals(event.getId(), eventGetDTO.getId());
        assertEquals(event.getName(), eventGetDTO.getTitle());
        assertEquals(event.getDescription(), eventGetDTO.getDesc());
        assertEquals(event.getStartTime(), eventGetDTO.getStart());
        assertEquals(event.getEndTime(), eventGetDTO.getEnd());
        assertEquals(event.getLabel(), eventGetDTO.getLabel());
    }

    @Test
    public void testGetModule_fromModule_toModuleGetDTO_success() {
        // create Module
        Module module = new Module();
        module.setId(1L);
        module.setName("name");
        module.setDescription("description");
        module.setProf_name("Prof. Mustermann");
        module.setZoom_link("www.link.com");

        // MAP -> create ModuleGetDTO
        ModuleGetDTO moduleGetDTO = DTOMapper.INSTANCE.convertEntityToModuleGetDTO(module);

        // check content
        assertEquals(module.getId(), moduleGetDTO.getId());
        assertEquals(module.getName(), moduleGetDTO.getName());
        assertEquals(module.getDescription(), moduleGetDTO.getDescription());
        assertEquals(module.getProf_name(), moduleGetDTO.getProf_name());
        assertEquals(module.getZoom_link(), moduleGetDTO.getZoom_link());
    }
}
