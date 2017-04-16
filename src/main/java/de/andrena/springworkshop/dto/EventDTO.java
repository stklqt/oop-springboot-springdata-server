package de.andrena.springworkshop.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventDTO {

    public String title;

    public String description;

    public LocalDateTime startTime;

    public LocalDateTime endTime;

    public List<SpeakerDTO> speakers;

    public TrackDTO track;

    public RoomDTO room;

    public TrackDTO getTrack() {
        return track;
    }

    public void setTrack(TrackDTO track) {
        this.track = track;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }

    public List<SpeakerDTO> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<SpeakerDTO> speakers) {
        this.speakers = speakers;
    }

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
