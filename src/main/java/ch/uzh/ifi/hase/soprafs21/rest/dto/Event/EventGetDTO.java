package ch.uzh.ifi.hase.soprafs21.rest.dto.Event;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;

import java.util.Date;

public class EventGetDTO {
    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private String description;
    private EventLabel label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventLabel getLabel() {
        return label;
    }

    public void setLabel(EventLabel label) {
        this.label = label;
    }

}
