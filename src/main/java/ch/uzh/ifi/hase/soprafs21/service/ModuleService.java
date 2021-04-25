package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.repository.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
