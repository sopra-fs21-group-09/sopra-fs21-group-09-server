package ch.uzh.ifi.hase.soprafs21.rest.dto.User;

public class JWTToken {
    private String jwtToken;

    public JWTToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
