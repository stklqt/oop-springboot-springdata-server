package de.andrena.springworkshop.dao;

import de.andrena.springworkshop.dto.EventDTO;
import de.andrena.springworkshop.dto.SpeakerDTO;
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
import java.util.stream.Collectors;

@Component
public class EventDaoImpl implements EventDao {

    @Autowired
    private RestTemplate restTemplate;

    private String scheme = "http";
    private String host = "localhost:8090";


    @Override
    public List<EventDTO> getAllEvents() {
        final String path = "/event";
        final UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.newInstance();
        final String url = apiUrlBuilder.scheme(scheme).host(host).path(path).build().toString();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypes.HAL_JSON);

        Resources<Resource<EventDTO>> eventResponse = sendRequest(url, new ParameterizedTypeReference<Resources<Resource<EventDTO>>>() {
        });

        injectSpeakers(eventResponse);
        return mapToDTOList(eventResponse);
    }

    private List<EventDTO> mapToDTOList(Resources<Resource<EventDTO>> eventResponse) {
        return eventResponse.getContent().stream().map(eventDTOResource -> eventDTOResource.getContent()).collect(Collectors.toList());
    }

    private void injectSpeakers(Resources<Resource<EventDTO>> eventResponse) {
        if (eventResponse != null) {
            ParameterizedTypeReference<Resources<Resource<SpeakerDTO>>> speakerType = new ParameterizedTypeReference<Resources<Resource<SpeakerDTO>>>() {
            };
            for (Resource<EventDTO> event : eventResponse) {
                String speakersUrl = event.getLink("speakers").getHref();
                Collection<Resource<SpeakerDTO>> speakers = sendRequest(speakersUrl, speakerType).getContent();

                List<SpeakerDTO> speakerDTOList = speakers.stream().map(speakerDTOResource -> speakerDTOResource.getContent()).collect(Collectors.toList());

                event.getContent().setSpeakers(speakerDTOList);
            }
        }
    }

    private <T> T sendRequest(String url, ParameterizedTypeReference<T> type) {
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, null, type);
        if (response != null) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public List<EventDTO> getEventsWithTitleContaining(String title) {
        final String path = "/event/search/findByTitleContaining";
        final UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.newInstance();
        final String url = apiUrlBuilder.scheme(scheme).host(host).path(path).queryParam("title", title).build().toString();

        Resources<Resource<EventDTO>> eventResponse = sendRequest(url, new ParameterizedTypeReference<Resources<Resource<EventDTO>>>() {
        });
        injectSpeakers(eventResponse);
        return mapToDTOList(eventResponse);
    }

    @Override
    public List<EventDTO> getEventsWithDescriptionContaining(String description) {
        final String path = "/event/search/findByDescriptionContaining";
        final UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.newInstance();
        final String url = apiUrlBuilder.scheme(scheme).host(host).path(path).queryParam("description", description).build().toString();

        Resources<Resource<EventDTO>> eventResponse = sendRequest(url, new ParameterizedTypeReference<Resources<Resource<EventDTO>>>() {
        });
        injectSpeakers(eventResponse);
        return mapToDTOList(eventResponse);
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
