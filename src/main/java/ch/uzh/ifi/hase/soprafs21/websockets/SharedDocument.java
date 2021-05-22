package ch.uzh.ifi.hase.soprafs21.websockets;

import ch.uzh.ifi.hase.soprafs21.entity.Group;
import ch.uzh.ifi.hase.soprafs21.entity.Task;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "DOCUMENTS")
public class SharedDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long groupId;

    private String data;

    public SharedDocument(Long groupId, String data) {
        this.groupId = groupId;
        this.data = data;
    }

    public SharedDocument() {
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
