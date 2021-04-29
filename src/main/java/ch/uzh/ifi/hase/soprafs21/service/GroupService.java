package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.GroupRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class GroupService extends AService{

    private final Logger log = LoggerFactory.getLogger(GroupService.class);

    private final GroupRepository groupRepository;
    private final UserService userService;
    private final ModuleService moduleService;


    @Autowired
    public GroupService(@Qualifier("groupRepository") GroupRepository groupRepository, @Lazy UserService userService,  @Lazy ModuleService moduleService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.moduleService = moduleService;
    }

    public List<Group> getGroups() {return this.groupRepository.findAll();
    }

    public void createGroupForUser(Group groupToBeCreated, Long creatorId) {
        User creator = userService.getUserById(creatorId);
        Group createdGroup = groupRepository.save(groupToBeCreated);
        groupRepository.flush();
        createdGroup.addCreator(creator);
        creator.addGroup(createdGroup);
    }


    public void createGroupForModule(Group groupToBeCreated, Long creatorId, Long moduleId) {
        User creator = userService.getUserById(creatorId);
        Module module = moduleService.getModuleById(moduleId);
        Group createdGroup = groupRepository.save(groupToBeCreated);
        groupRepository.flush();
        createdGroup.addCreator(creator);
        module.addGroup(createdGroup);
    }

    public Group getGroupById(Long groupId) {
        Group group;
        if (groupRepository.findById(groupId).isPresent()) {
            group = groupRepository.findById(groupId).get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "group was not found");
        }
        return group;
    }

}
