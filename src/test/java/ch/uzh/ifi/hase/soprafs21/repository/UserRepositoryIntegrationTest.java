package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureWebClient
public class UserRepositoryIntegrationTest {

    private User user;

    @Autowired
    private TestEntityManager entityManager;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setPassword("password");
        user.setMatrikelNr("1234");
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    public void findByName_success() {
        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getMatrikelNr(), user.getMatrikelNr());
        assertEquals(found.getToken(), user.getToken());
    }

    @Test
    public void findByID_success() {
        // when
        User found = userRepository.findById(user.getId()).get();

        // then
        assertNotNull(found.getId());
        assertEquals(found.getName(), user.getName());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getPassword(), user.getPassword());
        assertEquals(found.getMatrikelNr(), user.getMatrikelNr());
        assertEquals(found.getToken(), user.getToken());
    }

}
