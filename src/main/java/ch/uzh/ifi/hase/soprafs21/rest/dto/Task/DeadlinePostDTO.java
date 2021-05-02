package ch.uzh.ifi.hase.soprafs21.rest.dto.Task;


public class DeadlinePostDTO {


    private String time;
    private Boolean visible;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
