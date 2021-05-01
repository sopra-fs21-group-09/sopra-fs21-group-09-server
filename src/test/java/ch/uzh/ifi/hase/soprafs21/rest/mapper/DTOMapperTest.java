package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPutDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * DTOMapperTest
 * Tests if the mapping between the internal and the external/API representation works.
 */
public class DTOMapperTest {
    @Test
    public void testCreateUser_fromUserPostDTO_toUser_success() {
        // create UserPostDTO
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("password");
        userPostDTO.setUsername("username");
        userPostDTO.setName("name");

        // MAP -> Create user
        User user = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

        // check content
        assertEquals(userPostDTO.getPassword(), user.getPassword());
        assertEquals(userPostDTO.getUsername(), user.getUsername());
        assertEquals(userPostDTO.getName(), user.getName());
    }

    @Test
    public void testGetUser_fromUser_toUserGetDTO_success() {
        // create User
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firstname@lastname");
        user.setToken("1");
        user.setMatrikelNr("00-000-000");

        // MAP -> Create UserGetDTO
        UserGetDTO userGetDTO = DTOMapper.INSTANCE.convertEntityToUserGetDTO(user);

        // check content
        assertEquals(user.getId(), userGetDTO.getId());
        assertEquals(user.getUsername(), userGetDTO.getUsername());
        assertEquals(user.getToken(), userGetDTO.getToken());
        assertEquals(user.getName(), userGetDTO.getName());
        assertEquals(user.getMatrikelNr(), userGetDTO.getMatrikelNr());
    }

    @Test void testUpdateUser_fromUserPutDTO_toUser_success() {
        // create UserPutDTO
        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setName("name");
        userPutDTO.setUsername("username");
        userPutDTO.setMatrikelNr("00-000-000");

        // MAP -> Create User
        User user = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);

        // check content
        assertEquals(userPutDTO.getName(), user.getName());
        assertEquals(userPutDTO.getUsername(), user.getUsername());
        assertEquals(userPutDTO.getMatrikelNr(), user.getMatrikelNr());
        assertNull(user.getPassword());
        assertNull(user.getId());
        assertNull(user.getToken());
    }
}
