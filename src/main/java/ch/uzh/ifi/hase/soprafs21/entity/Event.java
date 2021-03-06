package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.EventLabel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SequenceGenerator(name="seq", initialValue=3, allocationSize=100)

@Entity
@Table(name = "EVENTS")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column
    private Boolean allDay;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private EventLabel label;

    @Column
    private Boolean instanceOfModule;

    @ManyToOne
    @JoinColumn(name = "MODULE_ID", referencedColumnName = "ID")
    private Module module;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

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

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public EventLabel getLabel() {
        return label;
    }

    public void setLabel(EventLabel label) {
        this.label = label;
    }

    public Boolean getInstanceOfModule() {
        return instanceOfModule;
    }

    public void setInstanceOfModule(Boolean instanceOfModule) {
        this.instanceOfModule = instanceOfModule;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}