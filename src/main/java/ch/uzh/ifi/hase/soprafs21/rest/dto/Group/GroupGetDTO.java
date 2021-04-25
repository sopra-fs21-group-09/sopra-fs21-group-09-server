package ch.uzh.ifi.hase.soprafs21.rest.dto.Group;

public class GroupGetDTO {
    private String name;
    //private User creator;
    private boolean priv;
    private int member_limit;
    //private List<User> users;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPriv() {
        return priv;
    }

    public void setPriv(boolean priv) {
        this.priv = priv;
    }

    public int getMember_limit() {
        return member_limit;
    }

    public void setMember_limit(int member_limit) {
        this.member_limit = member_limit;
    }
}
