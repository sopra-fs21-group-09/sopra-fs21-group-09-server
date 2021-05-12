package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.embeddable.UserTaskKey;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Long> {

    Set<Task> findAllByUsersUserId(Long userId);

//    Set<Task> findByUsersIdOrderByDeadlineTimeAsc(UserTaskKey users_id);

    Set<Task> findAllByGroupsGroupId(Long groupId);

}
