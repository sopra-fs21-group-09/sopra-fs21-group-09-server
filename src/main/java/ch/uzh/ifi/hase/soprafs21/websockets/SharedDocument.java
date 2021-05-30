package ch.uzh.ifi.hase.soprafs21.websockets;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "DOCUMENTS")
public class SharedDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "group_id")
    private Long id;

    @Column
    private String data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
