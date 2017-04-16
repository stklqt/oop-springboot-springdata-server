package de.andrena.springworkshop.entities;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Track {
    @Id
    private String track;

    public Track(String track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("track", track)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Track track1 = (Track) o;

        return new EqualsBuilder()
                .append(track, track1.track)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(track)
                .toHashCode();
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public static Track of(String track) {
        if (StringUtils.isNotBlank(track)){
            return new Track(track);
        }
        return null;
    }
}
