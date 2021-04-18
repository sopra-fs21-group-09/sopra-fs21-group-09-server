package ch.uzh.ifi.hase.soprafs21.rest.dto.User;

public class UserPostDTO {

    private String username;

    private String password;

    private String name;

    //TODO: maybe embed? name as first and last name

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
