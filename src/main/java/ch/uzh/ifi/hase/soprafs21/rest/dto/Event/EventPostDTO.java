package ch.uzh.ifi.hase.soprafs21.rest.dto.Event;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;

import java.util.Date;

public class EventPostDTO {
    private String title;
    private Date start;
    private Date end;
    private Boolean allDay;
    private String desc;
    private EventLabel label;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EventLabel getLabel() {
        return label;
    }

    public void setLabel(EventLabel label) {
        this.label = label;
    }

}
