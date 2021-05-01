package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;

import java.time.LocalDateTime;
import java.util.Date;

public class DeadlineGetDTO {

    private LocalDateTime time;
    private Boolean visible;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
