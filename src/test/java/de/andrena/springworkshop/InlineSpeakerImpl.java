package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.InlineSpeaker;
import de.andrena.springworkshop.entities.Speaker;

import java.time.LocalDateTime;
import java.util.Set;

public class InlineSpeakerImpl implements InlineSpeaker {
    private String room;
    private String title;
    private String description;
    private String track;
    private Set<Speaker> speakers;
    private LocalDateTime endTime;
    private LocalDateTime startTime;

    public InlineSpeakerImpl() {

    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public Set<Speaker> getSpeakers() {
        return this.speakers;
    }

    public void setSpeakers(Set<Speaker> speakers) {
        this.speakers = speakers;
    }

    @Override
    public String getTrack() {
        return this.track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    @Override
    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
