package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.embeddable.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(@Qualifier("taskRepository") TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTask(Long id) {
        if (taskRepository.findById(id).isEmpty()) {
            String errorMessage = "task was not found";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
        return taskRepository.findById(id).get();
    }

    public void createTask(Task newTask) {
        // TODO: find a better fitting ResponseStatusException
        // The only unique column of the Task entity is the ID, which is autogenerated... therefore there is never a conflict

        newTask = taskRepository.save(newTask);
        taskRepository.flush();

        log.debug("Created information for Task: {}", newTask);

    }

    public void updateTask(Long id, Task changesToTask) {
        // TODO: change rest specification to status code 404

        Task taskToBeUpdated;
        if (taskRepository.findById(id).isPresent()) {
            taskToBeUpdated = taskRepository.findById(id).get();
        }
        else {
            String errorMessage = "task was not found";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        String newName = returnIfChangedElseKeepOld(changesToTask.getName(), taskToBeUpdated.getName());
        String newDescription = returnIfChangedElseKeepOld(changesToTask.getDescription(), taskToBeUpdated.getDescription());
        Task newParentTask = returnIfChangedElseKeepOld(changesToTask.getParentTask(), taskToBeUpdated.getParentTask());
        Set<Task> newSubTasks = returnIfChangedElseKeepOld(changesToTask.getSubTasks(), taskToBeUpdated.getSubTasks());
        Deadline newDeadline = returnIfChangedElseKeepOld(changesToTask.getDeadline(), taskToBeUpdated.getDeadline());

        Task updatedTask = taskRepository.updateTaskById(newName, newDescription, newParentTask, newSubTasks, newDeadline, id);

        log.debug("Updated information for Task: {}", updatedTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private static <V> V returnIfChangedElseKeepOld(V change, V old) {
        return (change != null) ? change : old;
    }

}
