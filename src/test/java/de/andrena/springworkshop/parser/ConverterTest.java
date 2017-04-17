package de.andrena.springworkshop.parser;

import de.andrena.springworkshop.entities.SpeakerKey;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ConverterTest {

    private Converter converter = new Converter(LocalDate.now());

    @Test
    public void parseName() throws Exception {
        assertThat(converter.parseName("John Doe"), is(new SpeakerKey("John", "Doe")));
    }

}