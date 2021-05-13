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

    @Query(value = "SELECT * FROM TASKS t WHERE t.id in (SELECT gt.task_id FROM USERS_GROUPS ug left outer join GROUPS_TASKS gt on ug.group_id = gt.group_id WHERE user_id = (:userId))", nativeQuery = true)
    Set<Task> findAllGroupTasksForUserByUserId(@Param("userId") Long userId);

}
