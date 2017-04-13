package de.andrena.springworkshop.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Track {
    @Id
    @GeneratedValue
    private int id;
    private String track;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("track", track)
                .toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
