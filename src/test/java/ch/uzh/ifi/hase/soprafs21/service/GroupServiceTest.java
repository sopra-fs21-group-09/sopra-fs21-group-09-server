package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

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
}
