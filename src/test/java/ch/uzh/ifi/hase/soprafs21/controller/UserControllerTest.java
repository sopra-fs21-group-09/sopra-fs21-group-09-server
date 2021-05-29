package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPutDTO;
import ch.uzh.ifi.hase.soprafs21.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserControllerTest
 * This is a WebMvcTest which allows to test the UserController i.e. GET/POST request without actually sending them over the network.
 * This tests if the UserController works.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends ControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void createUser_validInput_userCreated() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("testUsername");

        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any())).willReturn(user);

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    public void createUser_invalidInput_excThrown() throws Exception {
        // given
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setPassword("Test User");
        userPostDTO.setUsername("testUsername");

        given(userService.createUser(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.CONFLICT));

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isConflict());
    }

    @Test
    public void givenUsers_whenGetOneUser_thenReturnJson() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setPassword("Firstname Lastname");
        user.setUsername("firstname@lastname");


        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(userService.getUserById(1L)).willReturn(user);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())));
    }

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setName("Firstname Lastname");
        user.setPassword("Firstname Lastname");
        user.setUsername("firstname@lastname");

        User user2 = new User();
        user2.setId(2L);
        user.setName("Firstname Lastname2");
        user2.setPassword("Firstname Lastname2");
        user2.setUsername("firstname@lastname2");


        List<User> allUsers2 = new ArrayList();
        allUsers2.add(user);
        allUsers2.add(user2);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(userService.getUsers()).willReturn(allUsers2);

        // when
        MockHttpServletRequestBuilder getRequest = get("/users").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(user.getId().intValue())))
                .andExpect(jsonPath("$[0].username", is(user.getUsername())))
                .andExpect(jsonPath("$[0].name", is(user.getName())))
                .andExpect(jsonPath("$[1].id", is(user2.getId().intValue())))
                .andExpect(jsonPath("$[1].username", is(user2.getUsername())))
                .andExpect(jsonPath("$[1].name", is(user2.getName())));
    }

    @Test
    public void successfulLogin_whenPostLogin_thenReturnJson() throws Exception {

    }

    @Test
    public void editUser_validInput_userEdited() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setPassword("Test User");
        user.setUsername("testUsername");

        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setUsername("testName");

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    public void editUser_invalidInput_excThrown() throws Exception {
        UserPutDTO userPutDTO = new UserPutDTO();
        userPutDTO.setUsername("testUsername");

        // what userService should return when updateUser is called
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(userService).updateUser(Mockito.any(), Mockito.any());

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder putRequest = put("/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userPutDTO));

        // then
        mockMvc.perform(putRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    public void joinModule_validInput_moduleJoined() throws Exception {
        doNothing().when(userService).addModuleToUser(Mockito.any(), Mockito.any());

        MockHttpServletRequestBuilder postRequest = post("/users/1/modules/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void leaveModule_validInput_moduleRemovedFromUser() throws Exception {
        doNothing().when(userService).removeModuleFromUser(Mockito.any(), Mockito.any());

        MockHttpServletRequestBuilder deleteRequest = delete("/users/1/modules/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    public void joinPublicGroup_validInput_groupJoined() throws Exception {
        doNothing().when(userService).addGroupToUser(Mockito.any(), Mockito.any());

        MockHttpServletRequestBuilder postRequest = post("/users/1/groups/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void joinPrivateGroup_validInput_groupJoined() throws Exception {

        doNothing().when(userService).addGroupToUser(Mockito.any(), Mockito.any());

        MockHttpServletRequestBuilder postRequest = post("/users/1/groups/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void leaveGroup_validInput_groupRemovedFromUser() throws Exception {
        doNothing().when(userService).removeGroupFromUser(Mockito.any(), Mockito.any());

        MockHttpServletRequestBuilder deleteRequest = delete("/users/1/groups/1").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(deleteRequest)
                .andExpect(status().isNoContent());

    }


}