package ch.uzh.ifi.hase.soprafs21.rest.dto.Event;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;

import java.util.Date;

public class EventPutDTO {
    private String title;
    private Date start;
    private Date end;
    private Boolean allDay;
    private String desc;
    private EventLabel label;
    private Long taskId;

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

    public boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
