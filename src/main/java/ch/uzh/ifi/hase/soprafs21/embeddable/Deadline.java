package ch.uzh.ifi.hase.soprafs21.embeddable;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
public class Deadline {

    @Temporal(TemporalType.DATE)
    private Date time;
    private boolean visible;

}
