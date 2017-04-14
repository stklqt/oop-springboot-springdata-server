package de.andrena.springworkshop.entities;

public class SpeakerKeyConverter implements org.springframework.core.convert.converter.Converter<String, SpeakerKey> {
    @Override
    public SpeakerKey convert(String key) {
        String[] keyParts = key.split("_");
        if (keyParts.length < 2) {
            throw new IllegalArgumentException("key does not match surname_firstname: " + key);
        }
        return new SpeakerKey(keyParts[1], keyParts[0]);
    }
}
