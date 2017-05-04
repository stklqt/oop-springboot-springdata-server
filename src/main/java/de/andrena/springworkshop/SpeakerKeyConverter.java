package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.SpeakerKey;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class SpeakerKeyConverter implements Converter<String, SpeakerKey> {
	@Override
	public SpeakerKey convert(String id) {
		String[] parts = id.split("_");
		if (parts.length < 2) {
			throw new IllegalArgumentException("key does not match surname_firstname: " + id);
		}
		return new SpeakerKey(parts[1], parts[0]);
	}

}
