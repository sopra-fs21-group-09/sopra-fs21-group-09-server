package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.Group.GroupPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserPostDTO;
import ch.uzh.ifi.hase.soprafs21.service.GroupService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
public class GroupControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupService groupService;

    @Test
    public void userCreateGroup_validInput_groupCreated() throws Exception {
        // given
        GroupPostDTO groupPostDTO = new GroupPostDTO();
        groupPostDTO.setName("testGroup");

        Mockito.doNothing().when(groupService).createGroupForUser(Mockito.any(), Mockito.any());

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/users/1/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(groupPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void userCreateGroupInModule_validInput_groupCreated() throws Exception {
        // given
        GroupPostDTO groupPostDTO = new GroupPostDTO();
        groupPostDTO.setName("testGroup");

        Mockito.doNothing().when(groupService).createGroupForModule(Mockito.any(), Mockito.any(),Mockito.any());

        // when/then -> do the request + validate the result
        MockHttpServletRequestBuilder postRequest = post("/modules/1/users/1/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(groupPostDTO));

        // then
        mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

    @Test
    public void givenGroups_whenGetGroups_thenReturnJsonArray() throws Exception {
        // given
        User user = new User();
        user.setId(1L);
        user.setPassword("Firstname Lastname");
        user.setUsername("creator");

        Group group1 = new Group();
        group1.setId(1L);
        group1.setName("group1");
        group1.setCreator(user);
        group1.setOpen(true);
        group1.setMemberLimit(10);
        group1.setMemberCount(1);

        Group group2 = new Group();
        group2.setId(2L);
        group2.setName("group2");
        group2.setCreator(user);
        group2.setOpen(true);
        group2.setMemberLimit(10);
        group2.setMemberCount(1);

        List<Group> allGroups= new ArrayList<>();
        allGroups.add(group1);
        allGroups.add(group2);

        // this mocks the UserService -> we define above what the userService should return when getUsers() is called
        given(groupService.getGroups()).willReturn(allGroups);

        // when
        MockHttpServletRequestBuilder getRequest = get("/groups").contentType(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(getRequest).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(group1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(group1.getName())))
                .andExpect(jsonPath("$[0].creator.username", is(group1.getCreator().getUsername())))
                .andExpect(jsonPath("$[0].open", is(group1.getOpen())))
                .andExpect(jsonPath("$[0].memberLimit", is(group1.getMemberLimit())))
                .andExpect(jsonPath("$[0].memberCount", is(group1.getMemberCount())))
                .andExpect(jsonPath("$[1].id", is(group2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(group2.getName())))
                .andExpect(jsonPath("$[1].creator.username", is(group2.getCreator().getUsername())))
                .andExpect(jsonPath("$[1].open", is(group2.getOpen())))
                .andExpect(jsonPath("$[1].memberLimit", is(group2.getMemberLimit())))
                .andExpect(jsonPath("$[1].memberCount", is(group2.getMemberCount())));
    }

}