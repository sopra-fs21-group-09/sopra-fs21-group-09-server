package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class EventService {

    private final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");


    @Autowired
    public EventService(@Qualifier("eventRepository") EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * get all Events
     */
    public List<Event> getEvents() {
        return this.eventRepository.findAll();
    }


    /**
     * create an Event
     */
    public void createEvent(Event event){
        eventRepository.save(event);
        eventRepository.flush();
    }

    /**
     * edit an Event
     */
    public void editEvent(Event eventInput, Long EventId) {

    }

    private void checkIfEventExists(Event event){
        Event eventById = eventRepository.findByEventId(event.getEventId());

        if (eventById == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event doesn't exist");
        }
    }
}
