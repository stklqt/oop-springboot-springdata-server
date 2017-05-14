package de.andrena.springworkshop.parser;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Converter {
    private static final Pattern NAME_REGEX = Pattern.compile("(.*)\\s+(.*)");
    private LocalDate date;

    public Converter(LocalDate date) {
        this.date = date;
    }

    public List<Event> extractEvents(Nodes nodes) {
        return nodes.events.stream().map(eventElement
                -> new Event(eventElement.title,
                eventElement.description,
                eventElement.startTime == null ? null : LocalDateTime.of(date, eventElement.startTime),
                eventElement.endTime == null ? null : LocalDateTime.of(date, eventElement.endTime),
                extractSpeakers(eventElement),
                extractTrack(eventElement.track),
                extractRoom(eventElement.room, eventElement.track)))
                .collect(Collectors.toList());
    }

    private String extractRoom(String room, String track) {
        if (StringUtils.isNotBlank(room)) {
            return room;
        }
        String[] trackAndRoom = track.split("<br>");
        if (trackAndRoom.length > 1) {
            return trackAndRoom[1];
        }
        return room;
    }

    private String extractTrack(String track) {
        String[] trackAndRoom = track.split("<br>");
        if (trackAndRoom.length > 1) {
            return trackAndRoom[0];
        }
        return track;
    }

    private Set<Speaker> extractSpeakers(EventElement nodes) {
        if (nodes.referent != null) {
            if (nodes.referent.nodes != null) {
                if (nodes.referent.nodes.referents != null) {
                    return nodes.referent.nodes.referents.stream().map(referentElement
							-> new Speaker(parseFirstName(referentElement.name), parseLastName(referentElement.name), referentElement.company, referentElement.bio)).collect(Collectors.toSet());

                }
            }
        }
        return Collections.emptySet();
    }

	String parseFirstName(String name) {
		Matcher matcher = NAME_REGEX.matcher(name);
		if (matcher.matches()) {
			return matcher.group(1);
		}
		throw new IllegalArgumentException("could not parse name " + name);
	}

	String parseLastName(String name) {
		Matcher matcher = NAME_REGEX.matcher(name);
		if (matcher.matches()) {
			return matcher.group(2);
		}
		throw new IllegalArgumentException("could not parse name " + name);
	}

}
