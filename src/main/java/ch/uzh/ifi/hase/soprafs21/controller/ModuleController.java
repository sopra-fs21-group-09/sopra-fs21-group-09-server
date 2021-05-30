package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.entity.Task;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Event.EventGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Module.ModuleGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Task.TaskPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.service.ModuleService;
import org.springframework.web.bind.annotation.RestController;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ModuleController {

    private final ModuleService moduleService;

    ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping("/modules")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ModuleGetDTO> getAllModules() {
        List<Module> modules = moduleService.getModules();
        List<ModuleGetDTO> moduleGetDTOs = new ArrayList<>();

        // convert each module to the API representation
        for (Module module : modules) {
            moduleGetDTOs.add(DTOMapper.INSTANCE.convertEntityToModuleGetDTO(module));
        }
        return moduleGetDTOs;
    }

    @GetMapping("/modules/{moduleId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ModuleGetDTO getModuleInformation(@PathVariable Long moduleId) {
        Module module = moduleService.getModuleById(moduleId);
        Module updatedModule = moduleService.loadModuleDetails(module);
        ModuleGetDTO moduleDTO = DTOMapper.INSTANCE.convertEntityToModuleGetDTO(updatedModule);
        return moduleDTO;
    }

    @GetMapping("/modules/{moduleId}/events")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<EventGetDTO> getEventsFromModule(@PathVariable Long moduleId) {
        Set<Event> events = moduleService.getEventsFromModule(moduleId);
        List<EventGetDTO> eventGetDTOs = new ArrayList<>();

        // convert each module to the API representation
        for (Event event : events) {
            eventGetDTOs.add(DTOMapper.INSTANCE.convertEntityToEventGetDTO(event));
        }
        return eventGetDTOs;
    }

    @PostMapping("/modules/{moduleId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTaskForModule(@PathVariable Long moduleId, @RequestBody TaskPostDTO taskPostDTO) {
        Task input = DTOMapper.INSTANCE.convertTaskPostDTOtoEntity(taskPostDTO);

        moduleService.createTaskForModule(moduleId, input);
    }

}

