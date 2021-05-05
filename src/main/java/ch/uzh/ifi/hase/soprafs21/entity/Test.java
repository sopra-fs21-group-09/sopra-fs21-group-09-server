package ch.uzh.ifi.hase.soprafs21.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Test {
    @JsonProperty("Objid")
    private int Objid;
    @JsonProperty("SmStext")
    private String SmStext;

    public int getObjid() {
        return Objid;
    }

    public void setObjid(int objid) {
        Objid = objid;
    }

    public String getSmStext() {
        return SmStext;
    }

    public void setSmStext(String smStext) {
        SmStext = smStext;
    }
}
