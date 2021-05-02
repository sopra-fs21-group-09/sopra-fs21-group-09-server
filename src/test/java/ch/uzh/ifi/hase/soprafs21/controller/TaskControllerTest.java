package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Deadline;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.DeadlinePostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPostDTO;
import ch.uzh.ifi.hase.soprafs21.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

@WebMvcTest(TaskController.class)
public class TaskControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void createTask_validInput_taskCreated() throws Exception {
        // given
        Task task = new Task();

        TaskPostDTO taskPostDTO = new TaskPostDTO();
        taskPostDTO.setName("name");
        taskPostDTO.setDescription("description");

        DeadlinePostDTO deadlinePostDTO = new DeadlinePostDTO();
        deadlinePostDTO.setTime("2021-01-01");
        deadlinePostDTO.setVisible(true);
        taskPostDTO.setDeadline(deadlinePostDTO);

        given(taskService.createTask(Mockito.any())).willReturn(task);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(taskPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());

    }

    @Test
    public void givenTask_whenGetOneTask_thenReturnJson() throws Exception {
        // given
        Task task = new Task();
        task.setId(1L);
        task.setName("name");
        task.setDescription("description");

        Deadline deadline = new Deadline();
        deadline.setTime(LocalDateTime.parse("2021-01-01T12:00:01"));
        deadline.setVisible(true);
        task.setDeadline(deadline);

        given(taskService.getTask(Mockito.any())).willReturn(task);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder getRequest = get("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(task.getName())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.deadline.time", is(deadline.getTime().toString())))
                .andExpect(jsonPath("$.deadline.visible", is(deadline.getVisible())));
    }


}