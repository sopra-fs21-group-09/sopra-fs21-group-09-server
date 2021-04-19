package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Date;

public class DeadlinePostDTO {


    private String time;
    private boolean visible;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
