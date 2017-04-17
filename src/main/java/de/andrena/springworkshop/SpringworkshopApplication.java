package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.parser.Converter;
import de.andrena.springworkshop.parser.Nodes;
import de.andrena.springworkshop.parser.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//for jsr310 java 8 java.time.*
@EntityScan(basePackageClasses = {SpringworkshopApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
public class SpringworkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringworkshopApplication.class, args);
        Nodes nodes1 = null;
        try {
            nodes1 = new Parser().unmarshal(new URL("https://entwicklertag.de/karlsruhe/2016/programm-export/1"));
            List<Event> events1 = new Converter(LocalDate.parse("2016-05-15")).extractEvents(nodes1);
            Set<Set<Speaker>> speakers = events1.stream().map(event -> event.getSpeakers()).collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
