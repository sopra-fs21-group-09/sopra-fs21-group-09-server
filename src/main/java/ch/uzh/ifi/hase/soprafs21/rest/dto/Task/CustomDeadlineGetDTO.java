package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;

import java.util.Date;

public class CustomDeadlineGetDTO {
    private Long id;
    private String title;
    private Date start;
    private Date end;
    private Boolean allDay = Boolean.TRUE;
    private String desc;
    private EventLabel label = EventLabel.DEADLINE;

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
