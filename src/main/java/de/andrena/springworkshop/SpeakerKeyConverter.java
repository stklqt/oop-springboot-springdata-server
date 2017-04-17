package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.entities.SpeakerKey;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SpeakerKeyConverter implements BackendIdConverter {
    @Override
    public Serializable fromRequestId(String id, Class<?> entityType) {
        if (entityType.isAssignableFrom(Speaker.class)) {
            String[] parts = id.split("_");
            if (parts.length < 2) {
                throw new IllegalArgumentException("key does not match surname_firstname: " + id);
            }
            return new SpeakerKey(parts[1], parts[0]);
        }
        return id;
    }

    @Override
    public String toRequestId(Serializable serializable, Class<?> entityType) {
        if (entityType.isAssignableFrom(Speaker.class) && serializable.getClass().isAssignableFrom(SpeakerKey.class)) {
            SpeakerKey key = (SpeakerKey) serializable;
            return key.getSurname() + "_" + key.getFirstName();
        }
        return serializable.toString();
    }

    @Override
    public boolean supports(Class<?> delimiter) {
        return delimiter.isAssignableFrom(Speaker.class);
    }
}
