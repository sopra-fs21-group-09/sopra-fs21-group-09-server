package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.beans.FeatureDescriptor;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

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

    @Autowired
    public EventService(@Qualifier("eventRepository") EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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

        Event eventToBeUpdated;
        if (eventRepository.findById(id).isPresent()) {
            eventToBeUpdated = eventRepository.findById(id).get();
        }
        else {
            String errorMessage = "event was not found";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
        BeanUtils.copyProperties(changesToEvent, eventToBeUpdated, getNullPropertyNames(changesToEvent));
        eventRepository.save(eventToBeUpdated);
        eventRepository.flush();

        log.debug("Updated information for Task: {}", eventToBeUpdated);
    }

    private void checkIfEventExists(Event event){
        Event eventById = eventRepository.findByEventId(event.getEventId());

        if (eventById == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Event doesn't exist");
        }
    }

    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
