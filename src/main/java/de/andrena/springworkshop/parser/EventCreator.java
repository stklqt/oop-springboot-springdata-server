package de.andrena.springworkshop.parser;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.repositories.EventRepository;
import de.andrena.springworkshop.repositories.SpeakerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//dont run, we have sql import files
//@Component
public class EventCreator implements ApplicationRunner {
    @Resource
    private Parser parser;
    @Resource
    private EventRepository eventRepository;
    @Resource
    private SpeakerRepository speakerRepository;
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        List<Event> events = fetchEvents("https://entwicklertag.de/karlsruhe/2016/programm-export/1", "2016-05-15");
        events.addAll(fetchEvents("https://entwicklertag.de/karlsruhe/2016/programm-export/2", "2016-05-16"));
        events.addAll(fetchEvents("https://entwicklertag.de/karlsruhe/2016/programm-export/3", "2016-05-17"));
        Set<Speaker> speakers = events.stream().map(event -> event.getSpeakers()).flatMap(speakerSet -> speakerSet.stream()).collect(Collectors.toSet());
        speakerRepository.save(speakers);
        eventRepository.save(events);
        System.out.println("speakers = " + speakers);
    }

    private List<Event> fetchEvents(String url, String day) throws IOException, JAXBException {
        Nodes nodes = parser.unmarshal(new URL(url));
        return new Converter(LocalDate.parse(day)).extractEvents(nodes);
    }
}
