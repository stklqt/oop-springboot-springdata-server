package de.andrena.springworkshop.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class EventElement {
    String title;
     String track;
     String room;
    @XmlElement(name="start-time")
     LocalTime startTime;
    @XmlElement(name="end-time")
     LocalTime endTime;
    @XmlElement(name="abstract")
     String description;
     ReferentRoot referent;
}
