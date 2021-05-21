package ch.uzh.ifi.hase.soprafs21;

import ch.uzh.ifi.hase.soprafs21.entity.Module;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.ModuleRepository;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@RestController
@SpringBootApplication
@EnableJpaRepositories(basePackages = "ch.uzh.ifi.hase.soprafs21.repository")
public class Application{
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Qualifier("moduleRepository")
    @Autowired
    private ModuleRepository moduleRepository;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            String results = restTemplate.getForObject(
                    "https://studentservices.uzh.ch/sap/opu/odata/uzh/vvz_data_srv/SmSearchSet?$skip=0&$top=5&$orderby=SmStext asc&$format=json", String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode uzhModuleNode = mapper.readTree(results);
            JsonNode result = uzhModuleNode.get("d").get("results");
            if (result.isArray()) {
                for (final JsonNode objNode : result) {
                    Module module = new Module();
                    module.setName(objNode.get("SmStext").asText());
                    module.setDescription(objNode.get("Description").asText());
                    module.setId(objNode.get("Objid").asLong());
                    moduleRepository.saveAndFlush(module);
                }
            }
            String NameURL = "";
            //https://namey.muffinlabs.com/name.json?count=10&type=female&with_surname=false&frequency=all
            if(!NameURL.isEmpty()){
            String names = restTemplate.getForObject(
                    NameURL, String.class);
            JsonNode userNode = mapper.readTree(names);
            if (userNode.isArray()) {
                for (final JsonNode objNode : userNode) {
                    User user = new User();
                    user.setUsername(objNode.asText());
                    user.setPassword("asdf");
                    user.setName(objNode.asText());
                    user.setToken(UUID.randomUUID().toString());
                    user.setMatrikelNr("Muahahaha");
                    userRepository.saveAndFlush(user);
                }
            }}

        };
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloWorld() {
        return "The application is running.";
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
