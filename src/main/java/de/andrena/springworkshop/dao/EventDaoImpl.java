package de.andrena.springworkshop.dao;

import de.andrena.springworkshop.dto.EventListDTO;
import de.andrena.springworkshop.dto.EventResponseDTO;
import de.andrena.springworkshop.entities.Event;
import de.andrena.springworkshop.entities.Speaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventDaoImpl implements EventDao {

    @Autowired
    private RestTemplate restTemplate;

    private String scheme = "http";
    private String host = "localhost:8090";


    @Override
    public List<Event> getAllEvents() {
        final String path = "/event";

        final UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.newInstance();
        final String url = apiUrlBuilder.scheme(scheme).host(host).path(path).build().toString();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypes.HAL_JSON);

        final HttpEntity<?> requestEntity = new HttpEntity<Void>(headers);
        Resources<Resource<Event>> responseBody = null;

        ParameterizedTypeReference<Resources<Resource<Event>>> expectedType = new ParameterizedTypeReference<Resources<Resource<Event>>>() {
        };
        ResponseEntity<Resources<Resource<Event>>> response = restTemplate.exchange("http://localhost:8090/event", HttpMethod.GET, requestEntity, expectedType);
        if (response != null) {
            responseBody = response.getBody();
            ParameterizedTypeReference<Resources<Resource<Speaker>>> speakerType = new ParameterizedTypeReference<Resources<Resource<Speaker>>>() {
            };
            for (Resource<Event> resource : responseBody) {
                String speakers = resource.getLink("speakers").getHref();
                ResponseEntity<Resources<Resource<Speaker>>> speakersResponse = restTemplate.exchange(speakers, HttpMethod.GET, requestEntity, speakerType);
                Collection<Resource<Speaker>> content = speakersResponse.getBody().getContent();
                Set<Speaker> speakerDTOList = content.stream().map(speakerDTOResource -> speakerDTOResource.getContent()).collect(Collectors.toSet());

                resource.getContent().setSpeakers(speakerDTOList);
            }
        }
        List<Event> eventDTOS = responseBody.getContent().stream().map(eventDTOResource -> eventDTOResource.getContent()).collect(Collectors.toList());

        return eventDTOS;
    }

    @Override
    public EventListDTO getEventsWithTitleContaining(String title) {
        final String path = "/event/search/findByTitleContaining";

        final UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.newInstance();
        final String url = apiUrlBuilder.scheme(scheme).host(host).path(path).queryParam("title", title).build().toString();

        final EventResponseDTO eventResponseDTO = get(url, EventResponseDTO.class);
        return eventResponseDTO._embedded;
    }

    @Override
    public EventListDTO getEventsWithDescriptionContaining(String description) {
        final String path = "/event/search/findByDescriptionContaining";

        final UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.newInstance();
        final String url = apiUrlBuilder.scheme(scheme).host(host).path(path).queryParam("description", description).build().toString();

        final EventResponseDTO eventResponseDTO = get(url, EventResponseDTO.class);
        return eventResponseDTO._embedded;
    }

    private <T> T get(final String url, final Class<T> entityClass) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<?> requestEntity = new HttpEntity<Void>(headers);
        return sendRequest(url, HttpMethod.GET, requestEntity, entityClass);
    }

    private <T> T sendRequest(final String url, final HttpMethod method, final HttpEntity<?> requestEntity,
                              final Class<T> responseEntityClass) {
        T responseBody = null;

        final ResponseEntity<T> response = restTemplate.exchange(url, method, requestEntity, responseEntityClass);
        if (response != null) {
            responseBody = response.getBody();
        }

        return responseBody;
    }

}
