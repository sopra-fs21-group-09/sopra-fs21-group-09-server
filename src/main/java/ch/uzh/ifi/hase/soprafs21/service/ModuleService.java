package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;
import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.EventRepository;
import ch.uzh.ifi.hase.soprafs21.repository.ModuleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ModuleService extends AService{
    private final Logger log = LoggerFactory.getLogger(ModuleService.class);

    private final ModuleRepository moduleRepository;
    private final EventRepository eventRepository;
    private final RestTemplate restTemplate;
    private final TaskService taskService;

    @Autowired
    public ModuleService(@Qualifier("moduleRepository") ModuleRepository moduleRepository,
                         @Qualifier("eventRepository") EventRepository eventRepository,
                         TaskService taskService,
                         RestTemplate restTemplate) {
        this.moduleRepository = moduleRepository;
        this.taskService = taskService;
        this.restTemplate = restTemplate;
        this.eventRepository = eventRepository;
    }

    public List<Module> getModules() {return this.moduleRepository.findAll();}

    public void loadModuleDetails(Module module){
        if(!moduleRepository.ModuleHasUser(module.getId())){
            try {
                String ModuleDetailsJson = restTemplate.getForObject(
                        "https://studentservices.uzh.ch/sap/opu/odata/uzh/vvz_data_srv/SmDetailsSet(SmObjId='"+module.getId()+"',PiqYear='2020',PiqSession='004')?sap-client=001&$expand=Partof,Organizations,Responsible,Events,Events/Persons,OfferPeriods&$format=json", String.class);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode ModuleDetailsResponse = mapper.readTree(ModuleDetailsJson);
                JsonNode uzhModuleDetails = ModuleDetailsResponse.get("d");
                module.setDescription(uzhModuleDetails.get("ObjectiveDescription").asText());
                String moduleName = uzhModuleDetails.get("SmText").asText();

                JsonNode ModuleEvents = ModuleDetailsResponse.get("d").get("Events").get("results");
                JsonNode vorlesungId = ModuleEvents.get(0).get("Objid");

                String EventsJson = restTemplate.getForObject(
                        "https://studentservices.uzh.ch/sap/opu/odata/uzh/vvz_data_srv/EDetailsSet(EObjId='"+vorlesungId.asText()+"',PiqYear='2020',PiqSession='004')?sap-client=001&$expand=Rooms,Persons,Schedule,Schedule,Rooms,Schedule/Persons,Modules,Links&$format=json", String.class);
                JsonNode EventsResponse = mapper.readTree(EventsJson);
                JsonNode events = EventsResponse.get("d").get("Schedule").get("results");

                for (final JsonNode objNode : events) {
                    Long dateLong = Long.valueOf(objNode.get("Evdat").asText().replaceAll("[^0-9]", ""));

                    Date date = new Date(dateLong);
                    Instant instant_off = date.toInstant();
                    LocalDate localDate = instant_off.atZone(ZoneId.systemDefault()).toLocalDate();
                    Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
                    Duration startDuration = Duration.parse(objNode.get("Beguz").asText());
                    Duration endDuration = Duration.parse(objNode.get("Enduz").asText());
                    Date startDate = Date.from(instant.plus(startDuration));
                    Date endDate = Date.from(instant.plus(endDuration));
                    log.info(endDate.toString());
                    Event event = new Event();
                    event.setName(moduleName);
                    event.setStartTime(startDate);
                    event.setEndTime(endDate);
                    event.setAllDay(Boolean.FALSE);
                    event.setLabel(EventLabel.LECTURE);
                    eventRepository.saveAndFlush(event);
                    module.addEvent(event);
                }
                moduleRepository.saveAndFlush(module);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Set<Event> getEventsFromModule(Long moduleId) {
        Module module = getModuleById(moduleId);
        return module.getEvents();
    }

    public Module getModuleById(Long id){
        Optional<Module> checkModule = moduleRepository.findById(id);
        if (checkModule.isPresent()) {
            return checkModule.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "module was not found");
        }
    }

    public void createTaskForModule(Long moduleId, Task input) {
        Module module = getModuleById(moduleId);
        var taskToAdd = taskService.createTask(input);
        module.addTask(taskToAdd);
        moduleRepository.saveAndFlush(module);
    }
}
