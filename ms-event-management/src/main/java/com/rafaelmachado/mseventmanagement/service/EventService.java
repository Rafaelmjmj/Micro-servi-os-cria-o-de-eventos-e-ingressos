package com.rafaelmachado.mseventmanagement.service;

import com.rafaelmachado.mseventmanagement.client.ViaCepClient;
import com.rafaelmachado.mseventmanagement.dto.EventRequestDTO;
import com.rafaelmachado.mseventmanagement.dto.EventResponseDTO;
import com.rafaelmachado.mseventmanagement.model.Event;
import com.rafaelmachado.mseventmanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ViaCepClient viaCepClient;

    @Autowired
    public EventService(EventRepository eventRepository, ViaCepClient viaCepClient) {
        this.eventRepository = eventRepository;
        this.viaCepClient = viaCepClient;
    }

    public EventResponseDTO createEvent(EventRequestDTO eventRequest) {
        var address = viaCepClient.buscarEnderecoPorCep(eventRequest.getCep());

        Event event = new Event(
                null,
                eventRequest.getEventName(),
                eventRequest.getDateTime(),
                eventRequest.getCep(),
                address.getLogradouro(),
                address.getBairro(),
                address.getLocalidade(),
                address.getUf()
        );

        Event savedEvent = eventRepository.save(event);
        return new EventResponseDTO(
                savedEvent.getId(),
                savedEvent.getEventName(),
                savedEvent.getDateTime(),
                savedEvent.getCep(),
                savedEvent.getLogradouro(),
                savedEvent.getBairro(),
                savedEvent.getCidade(),
                savedEvent.getUf()
        );
    }

    public Optional<EventResponseDTO> updateEvent(String id, EventRequestDTO eventRequest) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            var address = viaCepClient.buscarEnderecoPorCep(eventRequest.getCep());

            Event event = existingEvent.get();
            event.setEventName(eventRequest.getEventName());
            event.setDateTime(eventRequest.getDateTime());
            event.setCep(eventRequest.getCep());
            event.setLogradouro(address.getLogradouro());
            event.setBairro(address.getBairro());
            event.setCidade(address.getLocalidade());
            event.setUf(address.getUf());

            Event updatedEvent = eventRepository.save(event);
            return Optional.of(new EventResponseDTO(
                    updatedEvent.getId(),
                    updatedEvent.getEventName(),
                    updatedEvent.getDateTime(),
                    updatedEvent.getCep(),
                    updatedEvent.getLogradouro(),
                    updatedEvent.getBairro(),
                    updatedEvent.getCidade(),
                    updatedEvent.getUf()
            ));
        }
        return Optional.empty();
    }

    public Optional<EventResponseDTO> getEventById(String id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(e -> new EventResponseDTO(
                e.getId(),
                e.getEventName(),
                e.getDateTime(),
                e.getCep(),
                e.getLogradouro(),
                e.getBairro(),
                e.getCidade(),
                e.getUf()
        ));
    }

    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(e -> new EventResponseDTO(
                        e.getId(),
                        e.getEventName(),
                        e.getDateTime(),
                        e.getCep(),
                        e.getLogradouro(),
                        e.getBairro(),
                        e.getCidade(),
                        e.getUf()
                ))
                .collect(Collectors.toList());
    }

    public boolean deleteEvent(String id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<EventResponseDTO> getAllEventsSorted() {
        return eventRepository.findAll().stream()
                .sorted((e1, e2) -> e1.getDateTime().compareTo(e2.getDateTime()))
                .map(e -> new EventResponseDTO(
                        e.getId(),
                        e.getEventName(),
                        e.getDateTime(),
                        e.getCep(),
                        e.getLogradouro(),
                        e.getBairro(),
                        e.getCidade(),
                        e.getUf()
                ))
                .collect(Collectors.toList());
    }
}