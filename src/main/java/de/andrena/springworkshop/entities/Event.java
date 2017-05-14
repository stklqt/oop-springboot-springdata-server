package de.andrena.springworkshop.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Event {

    @Id
	@GeneratedValue()
	private Integer id;
	private String title;
	@Lob
	@Column(length=20971520)
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Speaker> speakers;
    private String track;
    private String room;

    public Event(String title, String description, LocalDateTime startTime, LocalDateTime endTime, Set<Speaker> speakers, String track, String room) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.speakers = speakers;
        this.track = track;
        this.room = room;
    }

    public Event() {

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("description", description)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("speakers", speakers)
                .append("track", track)
                .append("room", room)
                .toString();
    }

    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Set<Speaker> speakers) {
        this.speakers = speakers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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


    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
