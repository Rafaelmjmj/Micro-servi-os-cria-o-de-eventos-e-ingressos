package com.rafaelmachado.mseventmanagement;

import com.rafaelmachado.mseventmanagement.client.ViaCepClient;
import com.rafaelmachado.mseventmanagement.dto.EventRequestDTO;
import com.rafaelmachado.mseventmanagement.dto.EventResponseDTO;
import com.rafaelmachado.mseventmanagement.dto.ViaCepResponseDTO;
import com.rafaelmachado.mseventmanagement.model.Event;
import com.rafaelmachado.mseventmanagement.repository.EventRepository;
import com.rafaelmachado.mseventmanagement.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ViaCepClient viaCepClient;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_ValidRequest_ShouldReturnResponseWithAddress() {
        // Arrange
        EventRequestDTO request = new EventRequestDTO(
                "Tech Conference",
                LocalDateTime.of(2024, 10, 5, 14, 0),
                "12345678"
        );

        ViaCepResponseDTO mockAddress = new ViaCepResponseDTO();
        mockAddress.setLogradouro("Rua das Flores");
        mockAddress.setBairro("Centro");
        mockAddress.setLocalidade("São Paulo");
        mockAddress.setUf("SP");

        Event savedEvent = new Event(
                "event-123",
                request.getEventName(),
                request.getDateTime(),
                request.getCep(),
                mockAddress.getLogradouro(),
                mockAddress.getBairro(),
                mockAddress.getLocalidade(),
                mockAddress.getUf()
        );

        when(viaCepClient.buscarEnderecoPorCep(anyString())).thenReturn(mockAddress);
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // Act
        EventResponseDTO response = eventService.createEvent(request);

        // Assert
        assertNotNull(response.getId());
        assertEquals("Tech Conference", response.getEventName());
        assertEquals("Rua das Flores", response.getLogradouro());
        verify(viaCepClient).buscarEnderecoPorCep("12345678");
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEvent_ExistingEvent_ShouldUpdateAddressAndReturnDTO() {
        // Arrange
        String eventId = "event-456";
        EventRequestDTO request = new EventRequestDTO(
                "Updated Event",
                LocalDateTime.of(2024, 11, 1, 18, 30),
                "87654321"
        );

        ViaCepResponseDTO newAddress = new ViaCepResponseDTO();
        newAddress.setLogradouro("Avenida Paulista");
        newAddress.setBairro("Bela Vista");
        newAddress.setLocalidade("São Paulo");
        newAddress.setUf("SP");

        Event existingEvent = new Event(
                eventId,
                "Original Event",
                LocalDateTime.of(2024, 10, 1, 10, 0),
                "12345678",
                "Old Street",
                "Old Neighborhood",
                "Old City",
                "RJ"
        );

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(viaCepClient.buscarEnderecoPorCep("87654321")).thenReturn(newAddress);

        // Alterando os valores antes de salvar
        existingEvent.setEventName(request.getEventName());
        existingEvent.setDateTime(request.getDateTime());
        existingEvent.setCep(request.getCep());
        existingEvent.setLogradouro(newAddress.getLogradouro());
        existingEvent.setBairro(newAddress.getBairro());
        existingEvent.setCidade(newAddress.getLocalidade());
        existingEvent.setUf(newAddress.getUf());

        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        // Act
        Optional<EventResponseDTO> result = eventService.updateEvent(eventId, request);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Event", result.get().getEventName());
        assertEquals("Avenida Paulista", result.get().getLogradouro());
        assertEquals("SP", result.get().getUf());
        verify(eventRepository).save(existingEvent);
    }

    // Outros testes...

}