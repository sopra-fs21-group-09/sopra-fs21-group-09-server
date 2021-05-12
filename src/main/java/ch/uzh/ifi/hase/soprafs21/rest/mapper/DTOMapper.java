package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventPutDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPostDTO;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupGetDTO;

import ch.uzh.ifi.hase.soprafs21.entity.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.*;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventPostDTO;

import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Module.ModuleGetDTO;

import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPutDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g., UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for creating information (POST).
 */
@Mapper(uses = LocalDateTimeMapper.class)
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    //User
    UserGetDTO convertEntityToUserGetDTO(User user);
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "name", target = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "matrikelNr", ignore = true)
    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);
    @Mapping(source = "username", target = "username")
    @Mapping(source = "matrikelNr", target = "matrikelNr")
    @Mapping(source = "name", target = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);

    //Module
    ModuleGetDTO convertEntityToModuleGetDTO(Module module);

    //Group
    GroupGetDTO convertEntityToGroupGetDTO(Group group);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "open", target = "open")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "memberLimit", target = "memberLimit")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberCount", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Group convertGroupPostDTOtoEntity(GroupPostDTO groupPostDTO);

    //Task
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "deadline", target = "deadline")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
    @Mapping(target = "subTasks", ignore = true)
//    @Mapping(target = "module", ignore = true)
//    @Mapping(target = "group", ignore = true)
    Task convertTaskPostDTOtoEntity(TaskPostDTO taskPostDTO);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "deadline", target = "deadline")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentTask", ignore = true)
//    @Mapping(target = "module", ignore = true)
//    @Mapping(target = "group", ignore = true)
    Task convertTaskPutDTOtoEntity(TaskPutDTO taskPutDTO);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "deadline", target = "deadline")
    @Mapping(source = "subTasks", target =  "subTasks")
    TaskGetDTO convertEntityToTaskGetDTO(Task task);
    //Deadline
    @Mapping(source = "time", target = "time")
    @Mapping(source = "visible", target = "visible")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", ignore = true)
    Deadline convertDeadlinePostDTOtoEntity(DeadlinePostDTO deadlinePostDTO);
    @Mapping(target = "time", source = "time")
    @Mapping(target = "visible", source = "visible")
    DeadlineGetDTO convertEntityToDeadlineGetDTO(Deadline deadline);

    //Event
    @Mapping(target = "title", source = "name")
    @Mapping(target = "start", source = "startTime")
    @Mapping(target = "end", source = "endTime")
    @Mapping(target = "allDay", source = "allDay")
    @Mapping(target = "desc", source = "description")
    EventGetDTO convertEntityToEventGetDTO(Event event);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "startTime", source = "start")
    @Mapping(target = "endTime", source = "end")
    @Mapping(target = "allDay", source = "allDay")
    @Mapping(target = "description", source = "desc")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "user", ignore = true)
    Event convertEventPutDTOtoEntity(EventPutDTO eventPutDTO);

    @Mapping(target = "name", source = "title")
    @Mapping(target = "startTime", source = "start")
    @Mapping(target = "endTime", source = "end")
    @Mapping(target = "allDay", source = "allDay")
    @Mapping(target = "description", source = "desc")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "user", ignore = true)
    Event convertEventPostDTOtoEntity(EventPostDTO eventPostDTO);
}
