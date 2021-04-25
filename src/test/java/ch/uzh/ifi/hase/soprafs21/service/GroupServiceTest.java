package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class GroupServiceTest {

    @Mock
    private GroupRepository userRepository;

    @InjectMocks
    private GroupService groupService;

    private Group testGroup;

    @BeforeEach
    public void setup() {
    }

}
