package com.rafaelmachado.msticketmanager.controller;

import com.rafaelmachado.msticketmanager.dto.TicketRequestDTO;
import com.rafaelmachado.msticketmanager.dto.TicketResponseDTO;
import com.rafaelmachado.msticketmanager.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody TicketRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(request));
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicket(@PathVariable String id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(ticketService.getTicketsByCpf(cpf));
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> cancelTicket(@PathVariable String id) {
        ticketService.cancelTicketById(id);
        return ResponseEntity.noContent().build();
    }
}
