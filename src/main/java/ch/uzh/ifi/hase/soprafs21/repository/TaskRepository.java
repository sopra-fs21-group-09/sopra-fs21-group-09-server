package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM TASKS t WHERE t.id in (SELECT gt.task_id FROM GROUPS_TASKS gt WHERE gt.group_id = (:groupId))", nativeQuery = true)
    Set<Task> findAllTasksForGroupByGroupId(@Param("groupId") Long groupId);

    @Query(value = "SELECT * FROM TASKS t WHERE t.id in (SELECT ut.task_id FROM USERS_TASKS ut WHERE ut.user_id = (:userId))", nativeQuery = true)
    Set<Task> findAllTasksForUserByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM TASKS t WHERE t.id in (SELECT ut.task_id FROM USERS_TASKS ut WHERE ut.user_id = (:userId) and ut.completed = false)", nativeQuery = true)
    Set<Task> findAllOpenTasksForUserByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM TASKS t WHERE t.id in (SELECT ut.task_id FROM USERS_TASKS ut WHERE ut.user_id = (:userId) and ut.completed = true)", nativeQuery = true)
    Set<Task> findAllClosedTasksForUserByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM TASKS t WHERE t.id in (SELECT gt.task_id FROM USERS_GROUPS ug left outer join GROUPS_TASKS gt on ug.group_id = gt.group_id WHERE user_id = (:userId))", nativeQuery = true)
    Set<Task> findAllGroupTasksForUserByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM TASKS t WHERE t.id in (SELECT gt.task_id FROM USERS_GROUPS ug left outer join GROUPS_TASKS gt on ug.group_id = gt.group_id WHERE user_id = (:userId) and gt.completed = false)", nativeQuery = true)
    Set<Task> findAllOpenGroupTasksForUserByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM TASKS t WHERE t.id in (SELECT gt.task_id FROM USERS_GROUPS ug left outer join GROUPS_TASKS gt on ug.group_id = gt.group_id WHERE user_id = (:userId) and gt.completed = true)", nativeQuery = true)
    Set<Task> findAllClosedGroupTasksForUserByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE USERS_TASKS SET completed = (not completed) WHERE user_id = (:userId) and task_id = (:taskId)", nativeQuery = true)
    void toggleUserTaskCompleted(@Param("userId") Long userId, @Param("taskId") Long taskId);

}
