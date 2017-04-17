package de.andrena.springworkshop.parser;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;


@Component
public class Parser {
    public Nodes unmarshal(URL url) throws IOException, JAXBException {
        return (Nodes) JAXBContext.newInstance(Nodes.class).createUnmarshaller().unmarshal(url);
    }
}
