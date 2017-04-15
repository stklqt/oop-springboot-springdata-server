package de.andrena.springworkshop.dto;

import java.time.LocalDateTime;

public class EventDTO {

    public String title;

    public String description;

    public LocalDateTime startTime;

    public LocalDateTime endTime;

    //    public Set<SpeakerDTO> speakers;
//    public Track track;
//    public Room room;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

}
