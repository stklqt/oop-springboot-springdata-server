package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.parser.Converter;
import de.andrena.springworkshop.parser.Nodes;
import de.andrena.springworkshop.parser.Parser;
import org.junit.Test;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class ParserTest {
    @Test
    public void parseXml() throws Exception {
        Nodes nodes = new Parser().unmarshal(new URL("https://entwicklertag.de/karlsruhe/2016/programm-export/1"));
        List<Event> events = new Converter(LocalDate.parse("2016-05-15")).extractEvents(nodes);
        System.out.println("events = " + events);
    }

}
