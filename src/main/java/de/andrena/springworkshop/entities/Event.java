package de.andrena.springworkshop.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Event {

    @Id
    private int id;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private List<Speaker> speakers;
}
