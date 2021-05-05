package ch.uzh.ifi.hase.soprafs21.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class D {
    private List<Test> results;


    public List<Test> getResults() {
        return results;
    }

    public void setResults(List<Test> results) {
        this.results = results;
    }


}
