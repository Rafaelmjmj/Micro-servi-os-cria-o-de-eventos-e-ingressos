package com.rafaelmachado.mseventmanagement.service;

import com.rafaelmachado.mseventmanagement.dto.EventRequestDTO;
import com.rafaelmachado.mseventmanagement.dto.EventResponseDTO;
import com.rafaelmachado.mseventmanagement.model.Event;
import com.rafaelmachado.mseventmanagement.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventResponseDTO createEvent(EventRequestDTO eventRequest) {
        Event event = new Event(null, eventRequest.getEventName(), eventRequest.getDateTime(), eventRequest.getCep(), eventRequest.getLogradouro(), eventRequest.getBairro(), eventRequest.getCidade(), eventRequest.getUf());
        Event savedEvent = eventRepository.save(event);
        return new EventResponseDTO(savedEvent.getId(), savedEvent.getEventName(), savedEvent.getDateTime(), savedEvent.getCep(), savedEvent.getLogradouro(), savedEvent.getBairro(), savedEvent.getCidade(), savedEvent.getUf());
    }

    public Optional<EventResponseDTO> getEventById(String id) {
        return eventRepository.findById(id)
                .map(event -> new EventResponseDTO(event.getId(), event.getEventName(), event.getDateTime(), event.getCep(), event.getLogradouro(), event.getBairro(), event.getCidade(), event.getUf()));
    }

    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(event -> new EventResponseDTO(event.getId(), event.getEventName(), event.getDateTime(), event.getCep(), event.getLogradouro(), event.getBairro(), event.getCidade(), event.getUf()))
                .collect(Collectors.toList());
    }

    public Optional<EventResponseDTO> updateEvent(String id, EventRequestDTO eventRequest) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            event.setEventName(eventRequest.getEventName());
            event.setDateTime(eventRequest.getDateTime());
            event.setCep(eventRequest.getCep());
            event.setLogradouro(eventRequest.getLogradouro());
            event.setBairro(eventRequest.getBairro());
            event.setCidade(eventRequest.getCidade());
            event.setUf(eventRequest.getUf());
            Event updatedEvent = eventRepository.save(event);
            return Optional.of(new EventResponseDTO(updatedEvent.getId(), updatedEvent.getEventName(), updatedEvent.getDateTime(), updatedEvent.getCep(), updatedEvent.getLogradouro(), updatedEvent.getBairro(), updatedEvent.getCidade(), updatedEvent.getUf()));
        }
        return Optional.empty();
    }

    public boolean deleteEvent(String id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<EventResponseDTO> getAllEventsSorted() {
        return eventRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Event::getEventName)) // Ordena pelo nome do evento
                .map(event -> new EventResponseDTO(
                        event.getId(), event.getEventName(), event.getDateTime(),
                        event.getCep(), event.getLogradouro(), event.getBairro(),
                        event.getCidade(), event.getUf()
                ))
                .collect(Collectors.toList());
    }



}
