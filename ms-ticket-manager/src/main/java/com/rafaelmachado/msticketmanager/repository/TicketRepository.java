package com.rafaelmachado.msticketmanager.repository;

import com.rafaelmachado.msticketmanager.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByCpf(String cpf);
    List<Ticket> findByEventId(String eventId);
    Optional<Ticket> findByIdAndActiveTrue(String id);
}
