package de.andrena.springworkshop.parser;

import org.apache.commons.io.IOUtil;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.net.URL;

@Component
public class Parser {
    public Nodes unmarshal(URL url) throws IOException, JAXBException {
        System.out.println("string = " + IOUtil.toString(url.openStream()));
        Nodes unmarshal = (Nodes) JAXBContext.newInstance(Nodes.class).createUnmarshaller().unmarshal(url);
        System.out.println("unmarshal = " + unmarshal);
        return unmarshal;
    }
}
