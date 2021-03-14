package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * return all users
     * @return
     */
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    /**
     * return a the user corresponding to the ID, throw NotFound if userId doesn't exist
     * @param id
     * @return
     */
    public User getUserByID(Long id){
        if(this.userRepository.findByid(id) != null){
            return this.userRepository.findByid(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User wasn't found");
        }
    }

    /**
     * create the newUser
     * @param newUser
     * @return
     */
    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setCreationDate(LocalDate.now().toString());

        checkIfUserExists(newUser);

        // saves the given entity but data is only persisted in the database once flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    /**
     * Check if the user exists and if the password is correct
     * @param toCheckUser
     * @return
     */

    public User checkUser(User toCheckUser) {
        User existingUser = userRepository.findByUsername(toCheckUser.getUsername());
        String baseErrorMessage = "Login was unsuccesful. %s";

        if(existingUser != null){
            if(toCheckUser.getPassword().equals(existingUser.getPassword())){
                return existingUser;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "Wrong password"));
        }
        else{
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(baseErrorMessage, "User doesn't exist"));
    }}

    /**
     * method to edit the user --> copy all not null properties from source to target and save the edited User
     * @param src
     * @param target
     */
    public void editUser(User src, User target){
        if (src.getUsername() != target.getUsername()){
            checkIfUserExists(src);
        }
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        userRepository.save(target);
        userRepository.flush();
    }

    /**
     * This is a helper method that returns a list of all null properties, so only the not null properties are changed
     * when we try to edit
     * @param source
     * @return
     */

    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    /**
     * set a user online
     * @param user
     * @return
     */
    public User onlineUser(User user){
        user.setStatus(UserStatus.ONLINE);
        return user;
    }

    /**
     * This is a helper method that will check the uniqueness criteria of the username and the name
     * defined in the User entity. The method will do nothing if the input is unique and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws org.springframework.web.server.ResponseStatusException
     * @see User
     */
    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());

        String baseErrorMessage = "This %s already exists. Choose another one.";
        if (userByUsername != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format(baseErrorMessage, "username", "is"));
        }
    }
}
