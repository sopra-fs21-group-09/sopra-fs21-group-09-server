package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.embeddable.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import com.sun.xml.bind.v2.TODO;
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
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "deadlinePostDTO", target = "deadline")
    Task convertTaskPostDTOtoEntity(TaskPostDTO taskPostDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "deadlinePostDTO", target = "deadline")
    Task convertTaskPutDTOtoEntity(TaskPutDTO taskPutDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "deadline", target = "deadlineGetDTO")
    @Mapping(source = "subTasks", target =  "subTasks")
    TaskGetDTO convertEntityToTaskGetDTO(Task task);

    @Mapping(source = "time", target = "time")
    @Mapping(source = "visible", target = "visible")
    Deadline convertDeadlinePostDTOtoEntity(DeadlinePostDTO deadlinePostDTO);

    @Mapping(target = "time", source = "time")
    @Mapping(target = "visible", source = "visible")
    DeadlineGetDTO convertEntityToDeadlineGetDTO(Deadline deadline);
}
