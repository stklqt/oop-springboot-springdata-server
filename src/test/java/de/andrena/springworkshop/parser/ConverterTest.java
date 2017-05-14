package de.andrena.springworkshop.parser;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ConverterTest {

    private Converter converter = new Converter(LocalDate.now());

    @Test
    public void parseFirstName() throws Exception {
        assertThat(converter.parseFirstName("John Doe"), is("John"));
    }

    @Test
    public void parseLastName() throws Exception {
        assertThat(converter.parseLastName("John Doe"), is("Doe"));
    }

}