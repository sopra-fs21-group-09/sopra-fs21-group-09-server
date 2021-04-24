package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.embeddable.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query("update Task t set t.name = ?1, t.description = ?2, t.parentTask = ?3, t.subTasks = ?4, t.deadline = ?5 where t.id = ?6")
    Task updateTaskById(String name, String description, Task parentTask, List<Task> subTasks, Deadline deadline, Long id);

}
