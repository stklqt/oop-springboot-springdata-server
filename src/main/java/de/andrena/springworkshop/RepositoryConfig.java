package de.andrena.springworkshop;

import de.andrena.springworkshop.entities.Speaker;
import de.andrena.springworkshop.entities.SpeakerKeyConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);
        config.exposeIdsFor(Speaker.class);
    }

    @Override
    public void configureConversionService(ConfigurableConversionService conversionService) {
        super.configureConversionService(conversionService);
        conversionService.addConverter(new SpeakerKeyConverter());
    }
}
