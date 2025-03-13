package com.rafaelmachado.msticketmanager.repository;

import com.rafaelmachado.msticketmanager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    Optional<Ticket> findByIdAndActiveTrue(String id);
    List<Ticket> findByCpf(String cpf);
}
