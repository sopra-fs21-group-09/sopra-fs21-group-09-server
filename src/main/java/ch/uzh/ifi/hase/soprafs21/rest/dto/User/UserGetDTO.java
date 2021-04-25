package ch.uzh.ifi.hase.soprafs21.rest.dto.User;

public class UserGetDTO {
    private String username;
    private String token;
    private String name;
    private String matrikelNr;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMatrikelNr(String matrikelNr) {
        this.matrikelNr = matrikelNr;
    }
}
