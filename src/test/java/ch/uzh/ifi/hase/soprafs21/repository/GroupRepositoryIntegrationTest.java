package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureWebClient
public class GroupRepositoryIntegrationTest {

    private Group group;

    @Autowired
    private TestEntityManager entityManager;

    @Qualifier("groupRepository")
    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    public void setUp() {
        var user = new User();
        user.setId(1L);
        user.setPassword("Firstname Lastname");
        user.setUsername("creator");

        group = new Group();
        group.setName("group");
        group.setCreator(user);
        group.setOpen(true);
        group.setMemberLimit(10);
        group.setMemberCount(1);

        entityManager.persist(group);
    }

    @Test
    public void findById_success() throws Exception {
        // when
        var found = groupRepository.findById(group.getId()).get();

        // then
        assertNotNull(found.getId());
        assertEquals(group.getName(), found.getName());
        assertEquals(group.getCreator(), found.getCreator());
        assertEquals(group.getOpen(), found.getOpen());
        assertEquals(group.getMemberLimit(), found.getMemberLimit());
        assertEquals(group.getMemberCount(), found.getMemberCount());
        assertNull(found.getDescription());
        assertNull(found.getPassword());
    }
}
