@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = LocalTime.class, value = LocalTimeAdapter.class)
})
package de.andrena.springworkshop.parser;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalTime;