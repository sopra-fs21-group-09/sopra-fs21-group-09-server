package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Event;
import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.repository.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ModuleService extends AService{
    private final Logger log = LoggerFactory.getLogger(ModuleService.class);

    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(@Qualifier("moduleRepository") ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getModules() {return this.moduleRepository.findAll();}

    public Set<Event> getEventsFromModule(Long moduleId) {
        Module module = getModuleById(moduleId);
        return module.getEvents();
    }

    public Module getModuleById(Long id){
        Module module;
        if (moduleRepository.findById(id).isPresent()) {
            module = moduleRepository.findById(id).get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "module was not found");
        }
        return module;
    }


}
