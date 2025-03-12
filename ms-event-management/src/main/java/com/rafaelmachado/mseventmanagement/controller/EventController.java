package com.rafaelmachado.mseventmanagement.controller;

import com.rafaelmachado.mseventmanagement.dto.EventRequestDTO;
import com.rafaelmachado.mseventmanagement.dto.EventResponseDTO;
import com.rafaelmachado.mseventmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/br/com/compass/eventmanagement/v1")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/create-event")
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO eventRequest) {
        EventResponseDTO createdEvent = eventService.createEvent(eventRequest);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable String id) {
        return eventService.getEventById(id) // Corrigido de getEventId para getEventById
                .map(event -> ResponseEntity.ok(event)) // Correção para evitar erro de ambiguidade
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get-all-events")
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        List<EventResponseDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PutMapping("/update-event/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable String id, @RequestBody EventRequestDTO eventRequest) {
        return eventService.updateEvent(id, eventRequest)
                .map(event -> ResponseEntity.ok(event)) // Correção para evitar erro de ambiguidade
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        boolean deleted = eventService.deleteEvent(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<EventResponseDTO>> getAllEventsSorted() {
        List<EventResponseDTO> sortedEvents = eventService.getAllEventsSorted();
        return ResponseEntity.ok(sortedEvents);
    }
}