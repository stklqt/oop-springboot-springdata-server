package de.andrena.springworkshop.parser;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.repositories.EventRepository;
import de.andrena.springworkshop.repositories.SpeakerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventCreator implements ApplicationRunner {
    @Resource
    private Parser parser;
    @Resource
    private EventRepository eventRepository;
    @Resource
    private SpeakerRepository speakerRepository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Nodes nodes = parser.unmarshal(new URL("https://entwicklertag.de/karlsruhe/2016/programm-export/1"));
        List<Event> events = new Converter(LocalDate.parse("2016-05-15")).extractEvents(nodes);
        Set<Speaker> speakers = events.stream().map(event -> event.getSpeakers()).flatMap(speakerSet -> speakerSet.stream()).collect(Collectors.toSet());
        speakerRepository.save(speakers);
        eventRepository.save(events);
        System.out.println("speakers = " + speakers);
    }
}
