package ch.uzh.ifi.hase.soprafs21.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UZHModule {
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    @Override
    public String toString() {
        String str ="";
        for (Test test: d.getResults()){
            str += test.getObjid();
            str += test.getSmStext();
        }
        return str;
    }
}
