package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;
import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.ModuleRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventPutDTO;
import ch.uzh.ifi.hase.soprafs21.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void createEvent_validInput_eventCreated() throws Exception {
        // given
        EventPostDTO eventPostDTO = new EventPostDTO();
        eventPostDTO.setTitle("testEvent");
        eventPostDTO.setStart(new Date());
        eventPostDTO.setEnd(new Date());
        eventPostDTO.setDesc("description");
        eventPostDTO.setLabel(EventLabel.EXAM);

        Mockito.doNothing().when(eventService).createEvent(Mockito.any());

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(eventPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void editEvent_validInput_eventEdited() throws Exception {
        // given
        Event event = new Event();
        event.setId(1L);
        event.setName("testEvent");

        EventPutDTO eventPutDTO = new EventPutDTO();
        eventPutDTO.setTitle("testEvent");
        eventPutDTO.setStart(new Date());
        eventPutDTO.setEnd(new Date());
        eventPutDTO.setDesc("description");
        eventPutDTO.setLabel(EventLabel.EXAM);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(eventPutDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenEvents_whenGetEvents_thenReturnJsonArray() throws Exception {
        // given
        Event event1 = new Event();
        event1.setId(1L);
        event1.setName("testEvent");

        Event event2 = new Event();
        event2.setId(2L);
        event2.setName("test Event 2");

        List<Event> allEvents = new ArrayList();
        allEvents.add(event1);
        allEvents.add(event2);

        given(eventService.getEvents()).willReturn(allEvents);

        // when
        MockHttpServletRequestBuilder getRequest = get("/events").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(event1.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(event1.getName())))
                .andExpect(jsonPath("$[1].id", is(event2.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(event2.getName())));
    }
}