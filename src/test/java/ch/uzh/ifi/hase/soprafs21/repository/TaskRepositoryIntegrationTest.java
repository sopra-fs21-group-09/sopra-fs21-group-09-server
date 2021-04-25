package ch.uzh.ifi.hase.soprafs21.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class TaskRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Qualifier("taskRepository")
    @Autowired
    private TaskRepository taskRepository;

}
