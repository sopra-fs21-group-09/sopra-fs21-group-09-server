package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.embeddable.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@Transactional
public class TaskService extends AService{

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
        BeanUtils.copyProperties(changesToTask, taskToBeUpdated, getNullPropertyNames(changesToTask));
        taskRepository.save(taskToBeUpdated);
        taskRepository.flush();

        log.debug("Updated information for Task: {}", taskToBeUpdated);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void createSubTask(Long parentTaskId, Task newSubTask) {
        Task parentTask;
        if (taskRepository.findById(parentTaskId).isPresent()) {
            parentTask = taskRepository.findById(parentTaskId).get();
        }
        else {
            String errorMessage = "task was not found";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
        newSubTask = taskRepository.save(newSubTask);

        parentTask.addSubTask(newSubTask);
        taskRepository.flush();
        log.debug("Created information for sub task: {}", newSubTask);
    }
}
