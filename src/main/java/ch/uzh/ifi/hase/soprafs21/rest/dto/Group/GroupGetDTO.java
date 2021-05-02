package ch.uzh.ifi.hase.soprafs21.rest.dto.Group;

import ch.uzh.ifi.hase.soprafs21.rest.dto.User.UserGetDTO;

public class GroupGetDTO {
    private int id;
    private String name;
    private UserGetDTO creator;
    private Boolean open;
    private int memberLimit;
    private int memberCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserGetDTO getCreator() {
        return creator;
    }

    public void setCreator(UserGetDTO creator) {
        this.creator = creator;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public int getMemberLimit() {
        return memberLimit;
    }

    public void setMemberLimit(int memberLimit) {
        this.memberLimit = memberLimit;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}
