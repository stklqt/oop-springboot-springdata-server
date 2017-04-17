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
    }
}
