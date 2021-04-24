package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPutDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class TaskController {

    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createTask(@RequestBody TaskPostDTO taskPostDTO) {

        Task input = DTOMapper.INSTANCE.convertTaskPostDTOtoEntity(taskPostDTO);

        taskService.createTask(input);
    }

    @GetMapping("/tasks/{taskID}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TaskGetDTO getTask(@PathVariable Long taskID) {

        Task task = taskService.getTask(taskID);

        return DTOMapper.INSTANCE.convertEntityToTaskGetDTO(task);
    }

    @PutMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void updateTask(@PathVariable Long taskId, @RequestBody TaskPutDTO taskPutDTO) {
        Task input = DTOMapper.INSTANCE.convertTaskPutDTOtoEntity(taskPutDTO);

        taskService.updateTask(taskId, input);
    }

    @DeleteMapping("/task/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @PostMapping("/tasks/{taskId}/sub-tasks")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createSubTask(@PathVariable Long taskId, @RequestBody TaskPostDTO taskPostDTO) {
        Task input = DTOMapper.INSTANCE.convertTaskPostDTOtoEntity(taskPostDTO);

        taskService.createSubTask(taskId, input);
    }
}
