package de.andrena.springworkshop.parser;

import de.andrena.springworkshop.entities.Event;
import org.junit.Test;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ParserTest {
    @Test
    public void parseXml() throws Exception {
        Nodes nodes = new Parser().unmarshal(new URL("https://entwicklertag.de/karlsruhe/2016/programm-export/1"));
        List<Event> events = new Converter(LocalDate.parse("2016-05-15")).extractEvents(nodes);
        assertThat(events, hasSize(36));
    }

}
