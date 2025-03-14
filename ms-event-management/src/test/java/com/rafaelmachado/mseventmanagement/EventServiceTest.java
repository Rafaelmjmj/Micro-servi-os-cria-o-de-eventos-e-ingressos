package com.rafaelmachado.mseventmanagement;

import com.rafaelmachado.mseventmanagement.client.ViaCepClient;
import com.rafaelmachado.mseventmanagement.dto.EventResponseDTO;
import com.rafaelmachado.mseventmanagement.model.Event;
import com.rafaelmachado.mseventmanagement.repository.EventRepository;
import com.rafaelmachado.mseventmanagement.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void getEventById_ExistingEvent_ShouldReturnEventResponseDTO() {
        // Arrange
        String eventId = "event-789";
        Event event = new Event(
                eventId,
                "Test Event",
                LocalDateTime.of(2024, 9, 15, 10, 0),
                "98765432",
                "Avenida Central",
                "Centro",
                "Rio de Janeiro",
                "RJ"
        );

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Act
        Optional<EventResponseDTO> result = eventService.getEventById(eventId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Event", result.get().getEventName());
        assertEquals("Avenida Central", result.get().getLogradouro());
        verify(eventRepository).findById(eventId);
    }

    @Test
    void getEventById_NonExistingEvent_ShouldReturnEmptyOptional() {
        // Arrange
        String eventId = "non-existent-event";
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Act
        Optional<EventResponseDTO> result = eventService.getEventById(eventId);

        // Assert
        assertFalse(result.isPresent());
        verify(eventRepository).findById(eventId);
    }

    @Test
    void getAllEvents_ShouldReturnListOfEventResponseDTOs() {
        // Arrange
        List<Event> events = List.of(
                new Event("event-1", "Event One", LocalDateTime.now(), "12345678", "Street 1", "Bairro 1", "City 1", "SP"),
                new Event("event-2", "Event Two", LocalDateTime.now(), "87654321", "Street 2", "Bairro 2", "City 2", "RJ")
        );
        when(eventRepository.findAll()).thenReturn(events);

        // Act
        List<EventResponseDTO> result = eventService.getAllEvents();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Event One", result.get(0).getEventName());
        verify(eventRepository).findAll();
    }

    @Test
    void deleteEvent_ExistingEvent_ShouldReturnTrue() {
        // Arrange
        String eventId = "event-to-delete";
        when(eventRepository.existsById(eventId)).thenReturn(true);

        // Act
        boolean result = eventService.deleteEvent(eventId);

        // Assert
        assertTrue(result);
        verify(eventRepository).deleteById(eventId);
    }

    @Test
    void deleteEvent_NonExistingEvent_ShouldReturnFalse() {
        // Arrange
        String eventId = "non-existent-event";
        when(eventRepository.existsById(eventId)).thenReturn(false);

        // Act
        boolean result = eventService.deleteEvent(eventId);

        // Assert
        assertFalse(result);
        verify(eventRepository, never()).deleteById(eventId);
    }

    @Test
    void getAllEventsSorted_ShouldReturnSortedList() {
        // Arrange
        Event event1 = new Event("event-1", "Event One", LocalDateTime.of(2024, 8, 1, 10, 0), "12345678", "Street 1", "Bairro 1", "City 1", "SP");
        Event event2 = new Event("event-2", "Event Two", LocalDateTime.of(2024, 7, 1, 10, 0), "87654321", "Street 2", "Bairro 2", "City 2", "RJ");

        when(eventRepository.findAll()).thenReturn(List.of(event1, event2));

        // Act
        List<EventResponseDTO> result = eventService.getAllEventsSorted();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Event Two", result.get(0).getEventName()); // Deve estar primeiro por ter data anterior
        verify(eventRepository).findAll();
    }
}
