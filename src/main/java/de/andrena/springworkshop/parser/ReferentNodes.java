package de.andrena.springworkshop.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReferentNodes {
    @XmlElement(name = "node")
    List<ReferentElement> referents;
}
