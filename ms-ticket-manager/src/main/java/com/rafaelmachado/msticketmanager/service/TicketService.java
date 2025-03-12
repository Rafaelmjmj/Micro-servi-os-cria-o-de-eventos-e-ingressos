package com.rafaelmachado.msticketmanager.service;

import com.rafaelmachado.msticketmanager.dto.TicketRequestDTO;
import com.rafaelmachado.msticketmanager.dto.TicketResponseDTO;
import com.rafaelmachado.msticketmanager.exception.TicketNotFoundException;
import com.rafaelmachado.msticketmanager.model.Ticket;
import com.rafaelmachado.msticketmanager.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketResponseDTO createTicket(TicketRequestDTO request) {
        Ticket ticket = new Ticket(null, request.getEventId(), request.getCpf(), request.getName(), LocalDateTime.now(), true);
        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToResponse(savedTicket);
    }

    public TicketResponseDTO getTicketById(String id) {
        Ticket ticket = ticketRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new TicketNotFoundException("Ingresso não encontrado"));
        return mapToResponse(ticket);
    }

    public List<TicketResponseDTO> getTicketsByCpf(String cpf) {
        return ticketRepository.findByCpf(cpf)
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void cancelTicketById(String id) {
        Ticket ticket = ticketRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new TicketNotFoundException("Ingresso não encontrado"));
        ticket.setActive(false);
        ticketRepository.save(ticket);
    }

    private TicketResponseDTO mapToResponse(Ticket ticket) {
        return new TicketResponseDTO(ticket.getId(), ticket.getEventId(), ticket.getCpf(),
                ticket.getName(), ticket.getPurchaseDate(), ticket.isActive());
    }
}