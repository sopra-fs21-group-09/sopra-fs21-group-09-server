package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class EventService extends AService{

    private final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final UserService userService;

    @Autowired
    public EventService(@Qualifier("eventRepository") EventRepository eventRepository, @Lazy UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public List<Event> getEvents() {
        return this.eventRepository.findAll();
    }

    public void createEvent(Event event){
        eventRepository.save(event);
        eventRepository.flush();
    }

    public void updateEvent(Long id, Event changesToEvent) {
        // TODO: change rest specification to status code 404
        Event eventToBeUpdated = getEventById(id);
        checkEventParent(eventToBeUpdated);
        BeanUtils.copyProperties(changesToEvent, eventToBeUpdated, getNullPropertyNames(changesToEvent));
        eventRepository.save(eventToBeUpdated);
        eventRepository.flush();

        log.debug("Updated information for Task: {}", eventToBeUpdated);
    }

    private void checkEventParent(Event eventToBeUpdated) {
        if(Objects.isNull(eventToBeUpdated.getUser())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Editing an Event from a module is not allowed");
        }
    }

    public void createEventForUser(Event eventToBeCreated, Long userId) {
        User user = userService.getUserById(userId);
        Event createdEvent = eventRepository.save(eventToBeCreated);
        eventRepository.flush();
        user.addEvent(createdEvent);
    }

    public Event getEventById(Long id){
        Optional<Event> checkEvent = eventRepository.findById(id);
        if (checkEvent.isPresent()) {
            return checkEvent.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "event was not found");
        }
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
