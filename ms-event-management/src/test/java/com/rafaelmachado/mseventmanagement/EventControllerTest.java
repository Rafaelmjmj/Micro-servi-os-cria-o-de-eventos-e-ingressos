package com.rafaelmachado.mseventmanagement;

import com.rafaelmachado.mseventmanagement.controller.EventController;
import com.rafaelmachado.mseventmanagement.dto.EventRequestDTO;
import com.rafaelmachado.mseventmanagement.dto.EventResponseDTO;
import com.rafaelmachado.mseventmanagement.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private MockMvc mockMvc;

    @Test
    void createEvent_ValidRequest_ShouldReturnCreatedEvent() throws Exception {
        // Arrange
        EventRequestDTO request = new EventRequestDTO(
                "Tech Conference",
                LocalDateTime.of(2024, 10, 5, 14, 0),
                "12345678"
        );
        EventResponseDTO responseDTO = new EventResponseDTO(
                "event-123",
                "Tech Conference",
                LocalDateTime.of(2024, 10, 5, 14, 0),
                "12345678",
                "Rua das Flores",
                "Centro",
                "São Paulo",
                "SP"
        );

        when(eventService.createEvent(any(EventRequestDTO.class))).thenReturn(responseDTO);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        // Act & Assert
        mockMvc.perform(post("/br/com/compass/eventmanagement/v1/create-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"eventName\": \"Tech Conference\", \"dateTime\": \"2024-10-05T14:00:00\", \"cep\": \"12345678\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("Tech Conference"))
                .andExpect(jsonPath("$.id").value("event-123"));

        verify(eventService).createEvent(any(EventRequestDTO.class));
    }

    @Test
    void getEventById_EventFound_ShouldReturnEvent() throws Exception {
        // Arrange
        String eventId = "event-123";
        EventResponseDTO responseDTO = new EventResponseDTO(
                "event-123",
                "Tech Conference",
                LocalDateTime.of(2024, 10, 5, 14, 0),
                "12345678",
                "Rua das Flores",
                "Centro",
                "São Paulo",
                "SP"
        );

        when(eventService.getEventById(eventId)).thenReturn(Optional.of(responseDTO));

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        // Act & Assert
        mockMvc.perform(get("/br/com/compass/eventmanagement/v1/get-event/{id}", eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("Tech Conference"));

        verify(eventService).getEventById(eventId);
    }

    @Test
    void getEventById_EventNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        String eventId = "event-999";
        when(eventService.getEventById(eventId)).thenReturn(Optional.empty());

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        // Act & Assert
        mockMvc.perform(get("/br/com/compass/eventmanagement/v1/get-event/{id}", eventId))
                .andExpect(status().isNotFound());

        verify(eventService).getEventById(eventId);
    }

    @Test
    void getAllEvents_ShouldReturnListOfEvents() throws Exception {
        // Arrange
        EventResponseDTO event1 = new EventResponseDTO(
                "event-1",
                "Event 1",
                LocalDateTime.of(2024, 10, 5, 14, 0),
                "12345678",
                "Rua 1",
                "Bairro 1",
                "Cidade 1",
                "SP"
        );
        EventResponseDTO event2 = new EventResponseDTO(
                "event-2",
                "Event 2",
                LocalDateTime.of(2024, 10, 6, 14, 0),
                "87654321",
                "Rua 2",
                "Bairro 2",
                "Cidade 2",
                "RJ"
        );

        when(eventService.getAllEvents()).thenReturn(List.of(event1, event2));

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        // Act & Assert
        mockMvc.perform(get("/br/com/compass/eventmanagement/v1/get-all-events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("Event 1"))
                .andExpect(jsonPath("$[1].eventName").value("Event 2"));

        verify(eventService).getAllEvents();
    }

    @Test
    void updateEvent_ValidRequest_ShouldReturnUpdatedEvent() throws Exception {
        // Arrange
        String eventId = "event-123";
        EventRequestDTO request = new EventRequestDTO(
                "Updated Event",
                LocalDateTime.of(2024, 11, 1, 18, 0),
                "87654321"
        );
        EventResponseDTO updatedEvent = new EventResponseDTO(
                eventId,
                "Updated Event",
                LocalDateTime.of(2024, 11, 1, 18, 0),
                "87654321",
                "Avenida Paulista",
                "Bela Vista",
                "São Paulo",
                "SP"
        );

        when(eventService.updateEvent(eq(eventId), any(EventRequestDTO.class))).thenReturn(Optional.of(updatedEvent));

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        // Act & Assert
        mockMvc.perform(put("/br/com/compass/eventmanagement/v1/update-event/{id}", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"eventName\": \"Updated Event\", \"dateTime\": \"2024-11-01T18:00:00\", \"cep\": \"87654321\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("Updated Event"));

        verify(eventService).updateEvent(eq(eventId), any(EventRequestDTO.class));
    }

    @Test
    void deleteEvent_EventFound_ShouldReturnOk() throws Exception {
        // Arrange
        String eventId = "event-123";
        when(eventService.deleteEvent(eventId)).thenReturn(true);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        // Act & Assert
        mockMvc.perform(delete("/br/com/compass/eventmanagement/v1/delete-event/{id}", eventId))
                .andExpect(status().isOk());

        verify(eventService).deleteEvent(eventId);
    }

    @Test
    void deleteEvent_EventNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        String eventId = "event-999";
        when(eventService.deleteEvent(eventId)).thenReturn(false);

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        // Act & Assert
        mockMvc.perform(delete("/br/com/compass/eventmanagement/v1/delete-event/{id}", eventId))
                .andExpect(status().isNotFound());

        verify(eventService).deleteEvent(eventId);
    }

    @Test
    void getAllEventsSorted_ShouldReturnSortedEvents() throws Exception {
        // Arrange
        EventResponseDTO event1 = new EventResponseDTO(
                "event-1",
                "Event 1",
                LocalDateTime.of(2024, 10, 5, 14, 0),
                "12345678",
                "Rua 1",
                "Bairro 1",
                "Cidade 1",
                "SP"
        );
        EventResponseDTO event2 = new EventResponseDTO(
                "event-2",
                "Event 2",
                LocalDateTime.of(2024, 10, 6, 14, 0),
                "87654321",
                "Rua 2",
                "Bairro 2",
                "Cidade 2",
                "RJ"
        );

        when(eventService.getAllEventsSorted()).thenReturn(List.of(event1, event2));

        // Setup MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        // Act & Assert
        mockMvc.perform(get("/br/com/compass/eventmanagement/v1/get-all-events/sorted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("Event 1"))
                .andExpect(jsonPath("$[1].eventName").value("Event 2"));

        verify(eventService).getAllEventsSorted();
    }
}