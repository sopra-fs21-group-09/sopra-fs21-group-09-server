package ch.uzh.ifi.hase.soprafs21.rest.dto;

public class ModuleGetDTO {
    private Long id;
    private String name;
    private String description;
    private String prof_name;
    private String zoom_link;
    //private Event lecture_time;
    //private Event exam;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProf_name(String prof_name) {
        this.prof_name = prof_name;
    }

    public void setZoom_link(String zoom_link) {
        this.zoom_link = zoom_link;
    }
}
