package ch.uzh.ifi.hase.soprafs21.rest.dto.User;

public class UserPutDTO {
    private String username;
    private String name;
    private String matrikelNr;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMatrikelNr(String matrikelNr) {
        this.matrikelNr = matrikelNr;
    }
}
