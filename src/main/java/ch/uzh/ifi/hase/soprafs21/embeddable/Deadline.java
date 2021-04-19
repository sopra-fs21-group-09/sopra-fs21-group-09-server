package ch.uzh.ifi.hase.soprafs21.embeddable;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class Deadline implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime time;
    private boolean visible;

    public Deadline() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
