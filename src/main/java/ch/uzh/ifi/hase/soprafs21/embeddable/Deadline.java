package ch.uzh.ifi.hase.soprafs21.embeddable;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class Deadline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.DATE)
    private Date when;
    private boolean visibleInCalendar;

    public Deadline() {

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getWhen() {
        return when;
    }

    public void setWhen(Date when) {
        this.when = when;
    }

    public boolean isVisibleInCalendar() {
        return visibleInCalendar;
    }

    public void setVisibleInCalendar(boolean visibleInCalendar) {
        this.visibleInCalendar = visibleInCalendar;
    }
}
