package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Module.ModuleGetDTO;
import ch.uzh.ifi.hase.soprafs21.service.ModuleService;
import org.springframework.web.bind.annotation.RestController;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    //TODO: add further mappings (get /modules/{moduleID}, ...)
}

