package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.service.ModuleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModuleService moduleService;

    @Test
    public void givenModules_whenGetModules_thenReturnJsonArray() throws Exception {
        // given

        Module module1 = new Module();
        module1.setId(1L);
        module1.setName("module1");
        module1.setDescription("description");
        module1.setProf_name("prof");

        Module module2 = new Module();
        module2.setId(1L);
        module2.setName("module2");
        module2.setDescription("description2");
        module2.setProf_name("prof2");

        List<Module> allModules= new ArrayList<>();
        allModules.add(module1);
        allModules.add(module2);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(moduleService.getModules()).willReturn(allModules);

        // when
        MockHttpServletRequestBuilder getRequest = get("/modules").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(module1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(module1.getName())))
                .andExpect(jsonPath("$[0].description", is(module1.getDescription())))
                .andExpect(jsonPath("$[0].prof_name", is(module1.getProf_name())))
                .andExpect(jsonPath("$[1].id", is(module2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(module2.getName())))
                .andExpect(jsonPath("$[1].description", is(module2.getDescription())))
                .andExpect(jsonPath("$[1].prof_name", is(module2.getProf_name())));
    }


    @Test
    public void givenEvents_whenGetEventsFromModule_thenReturnJsonArray() throws Exception {
        // given
        Module module1 = new Module();
        module1.setId(1L);
        module1.setName("module1");
        module1.setDescription("description");
        module1.setProf_name("prof");

        Event event1 = new Event();
        event1.setId(1L);
        event1.setName("testEvent");

        Event event2 = new Event();
        event2.setId(2L);
        event2.setName("test Event 2");

        module1.addEvent(event1);
        module1.addEvent(event2);

        Set<Event> allEvents = new HashSet<>();
        allEvents.add(event1);
        allEvents.add(event2);

        given(moduleService.getEventsFromModule(Mockito.any())).willReturn(allEvents);

        // when
        MockHttpServletRequestBuilder getRequest = get("/modules/1/events").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(event1.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(event1.getName())))
                .andExpect(jsonPath("$[1].id", is(event2.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(event2.getName())));
    }

}