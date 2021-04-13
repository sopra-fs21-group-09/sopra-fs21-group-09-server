package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.Date;

public class DeadlineGetDTO {

    private Date time;
    private boolean visible;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
