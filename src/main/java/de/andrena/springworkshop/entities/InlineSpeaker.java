package de.andrena.springworkshop.entities;

import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.Set;

@Projection(name = "inlineSpeaker", types = Event.class)
public interface InlineSpeaker {
    String getTitle();

    String getDescription();

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();

    Set<Speaker> getSpeakers();

    String getTrack();

    String getRoom();

}
