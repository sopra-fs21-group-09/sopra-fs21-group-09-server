package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.embeddable.UserTaskKey;
import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private EventService eventService;

    private Event testEvent;

    @BeforeEach
    public void SetUp() {
        MockitoAnnotations.openMocks(this);
        User testUser = new User();
        testUser.setId(1L);
        testUser.setPassword("testName");
        testUser.setUsername("testUsername");

        testEvent = new Event();
        testEvent.setName("test Event");
        testEvent.setId(1L);
        testEvent.setDescription("description");
        testEvent.setUser(testUser);

        Mockito.when(eventRepository.save(Mockito.any())).thenReturn(testEvent);
        Mockito.when(eventRepository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(testEvent));
    }

    @Test
    public void updateEvent_validInputs_success() {
        Event updatesToEvent = new Event();
        updatesToEvent.setName("updated Name");
        updatesToEvent.setDescription("updated Description");

        eventService.updateEvent(1L, updatesToEvent);

        // then
        Mockito.verify(eventRepository, Mockito.times(1)).save(Mockito.any());

        assertEquals(testEvent.getName(), updatesToEvent.getName());
        assertEquals(testEvent.getDescription(), updatesToEvent.getDescription());
    }

    @Test
    public void createEventForUser_validInputs_success(){
        User testUser = new User();
        testUser.setId(1L);
        testUser.setPassword("testName");
        testUser.setUsername("testUsername");
        testUser.setEvents(new HashSet<>());

        Mockito.when(userService.getUserById(Mockito.any())).thenReturn(testUser);

        eventService.createEventForUser(testEvent, 1L);

        assertEquals(testUser.getEvents().iterator().next(), testEvent);
    }
}
