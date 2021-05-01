package ch.uzh.ifi.hase.soprafs21.rest.dto.Event;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;

import java.util.Date;

public class EventGetDTO {
    private Long id;
    private String title;
    private Date start;
    private Date end;
    private String desc;
    private EventLabel label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
