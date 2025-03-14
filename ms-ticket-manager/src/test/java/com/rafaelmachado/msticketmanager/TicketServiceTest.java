package com.rafaelmachado.msticketmanager;

import com.rafaelmachado.msticketmanager.dto.TicketRequestDTO;
import com.rafaelmachado.msticketmanager.dto.TicketResponseDTO;
import com.rafaelmachado.msticketmanager.exception.TicketNotFoundException;
import com.rafaelmachado.msticketmanager.model.Ticket;
import com.rafaelmachado.msticketmanager.repository.TicketRepository;
import com.rafaelmachado.msticketmanager.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private TicketRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new TicketRequestDTO("event123", "12345678900", "Rafael Machado");
        ticket = new Ticket("1", "event123", "12345678900", "Rafael Machado", LocalDateTime.now(), true);
    }

    @Test
    void createTicket_ShouldReturnTicketResponseDTO() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        TicketResponseDTO response = ticketService.createTicket(requestDTO);

        assertNotNull(response);
        assertEquals(ticket.getId(), response.getId());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void getTicketById_ShouldReturnTicketResponseDTO_WhenTicketExists() {
        when(ticketRepository.findByIdAndActiveTrue("1")).thenReturn(Optional.of(ticket));
        TicketResponseDTO response = ticketService.getTicketById("1");

        assertNotNull(response);
        assertEquals(ticket.getId(), response.getId());
        verify(ticketRepository, times(1)).findByIdAndActiveTrue("1");
    }

    @Test
    void getTicketById_ShouldThrowException_WhenTicketDoesNotExist() {
        when(ticketRepository.findByIdAndActiveTrue("1")).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById("1"));
        verify(ticketRepository, times(1)).findByIdAndActiveTrue("1");
    }

    @Test
    void getTicketsByCpf_ShouldReturnListOfTickets() {
        when(ticketRepository.findByCpf("12345678900")).thenReturn(List.of(ticket));
        List<TicketResponseDTO> response = ticketService.getTicketsByCpf("12345678900");

        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        verify(ticketRepository, times(1)).findByCpf("12345678900");
    }

    @Test
    void cancelTicketById_ShouldSetTicketAsInactive() {
        when(ticketRepository.findByIdAndActiveTrue("1")).thenReturn(Optional.of(ticket));
        doAnswer(invocation -> {
            ticket.setActive(false);
            return null;
        }).when(ticketRepository).save(any(Ticket.class));

        ticketService.cancelTicketById("1");

        assertFalse(ticket.isActive());
        verify(ticketRepository, times(1)).findByIdAndActiveTrue("1");
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }
}

