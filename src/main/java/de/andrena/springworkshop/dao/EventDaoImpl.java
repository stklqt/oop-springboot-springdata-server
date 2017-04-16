package de.andrena.springworkshop.dao;

import de.andrena.springworkshop.dto.EventDTO;
import de.andrena.springworkshop.dto.EventListDTO;
import de.andrena.springworkshop.dto.EventResponseDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class EventDaoImpl implements EventDao {

    private RestTemplate restService = new RestTemplate();

    private String scheme = "http";
    private String host = "localhost:8090";

    @Override
    public EventListDTO getAllEvents() {
        final String path = "/event";

        final UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.newInstance();
        final String url = apiUrlBuilder.scheme(scheme).host(host).path(path).build().toString();

        final EventResponseDTO eventResponseDTO = get(url, EventResponseDTO.class);

        //TODO: fill Speakers belonging to Event
        return eventResponseDTO._embedded;
    }

    @Override
    public EventDTO getEventWithTitle(String title) {
        final String path = "/event/search/findByTitle";

        final UriComponentsBuilder apiUrlBuilder = UriComponentsBuilder.newInstance();
        final String url = apiUrlBuilder.scheme(scheme).host(host).path(path).queryParam("title", title).build().toString();

        return get(url, EventDTO.class);
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

        final ResponseEntity<T> response = restService.exchange(url, method, requestEntity, responseEntityClass);
        if (response != null) {
            responseBody = response.getBody();
        }

        return responseBody;
    }

}
