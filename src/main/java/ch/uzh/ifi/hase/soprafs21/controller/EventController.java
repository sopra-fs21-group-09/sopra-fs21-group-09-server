package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventPutDTO;
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
    public List<EventGetDTO> getAllEvents() {
        List<Event> events = eventService.getEvents();
        List<EventGetDTO> eventGetDTOs = new ArrayList<EventGetDTO>();

        for (Event event: events) {
            eventGetDTOs.add(DTOMapper.INSTANCE.convertEntityToEventGetDTO(event));
        }
        return eventGetDTOs;
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void createEvent(@RequestBody EventPostDTO eventPostDTO){
        Event eventInput = DTOMapper.INSTANCE.convertEventPostDTOtoEntity(eventPostDTO);

        eventService.createEvent(eventInput);
    }

    @PutMapping("/events/{EventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void editEvent(@PathVariable Long EventId, @RequestBody EventPutDTO eventPutDTO) {
        Event input = DTOMapper.INSTANCE.convertEventPutDTOtoEntity(eventPutDTO);

        eventService.updateEvent(EventId, input);
    }


}