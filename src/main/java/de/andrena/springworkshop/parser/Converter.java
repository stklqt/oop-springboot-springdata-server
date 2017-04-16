package de.andrena.springworkshop.parser;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.entities.SpeakerKey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Converter {
    private LocalDate date;

    public Converter(LocalDate date) {
        this.date = date;
    }

    public List<Event> extractEvents(Nodes nodes) {
        return nodes.events.stream().map(eventElement
                -> new Event(eventElement.title, eventElement.description, eventElement.startTime == null ? null : LocalDateTime.of(date, eventElement.startTime),
                eventElement.endTime == null ? null : LocalDateTime.of(date, eventElement.endTime),
                extractSpeakers(eventElement),
                eventElement.track, eventElement.room)).collect(Collectors.toList());
    }

    public Set<Speaker> extractSpeakers(EventElement nodes) {
        if (nodes.referent != null) {
            if (nodes.referent.nodes != null) {
                if (nodes.referent.nodes.referents != null) {
                    return nodes.referent.nodes.referents.stream().map(referentElement
                            -> new Speaker(parseName(referentElement.name), referentElement.company, referentElement.bio)).collect(Collectors.toSet());

                }
            }
        }
        return Collections.emptySet();
    }

    private SpeakerKey parseName(String name) {
        String[] nameParts = name.split(" ");
        String lastname = nameParts[nameParts.length - 1];
        String firstname = name.split(lastname)[0];
        return new SpeakerKey(firstname, lastname);
    }
}
