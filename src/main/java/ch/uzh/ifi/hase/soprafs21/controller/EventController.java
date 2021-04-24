package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventDTO;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class EventController {

    private final EventService eventService;

    EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventService.getEvents();
        List<EventDTO> eventDTOs = new ArrayList<EventDTO>();

        for (Event event: events) {
            eventDTOs.add(DTOMapper.INSTANCE.convertEntityToEventDTO(event));
        }
        return eventDTOs;
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createEvent(@RequestBody EventDTO eventDTO){
        Event eventInput = DTOMapper.INSTANCE.convertEventDTOtoEntity(eventDTO);

        eventService.createEvent(eventInput);
    }

    @PutMapping("/events/{EventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void editEvent(@PathVariable Long EventId, @RequestBody EventDTO eventDTO) {
        Event input = DTOMapper.INSTANCE.convertEventDTOtoEntity(eventDTO);

        eventService.updateEvent(EventId, input);
    }
}