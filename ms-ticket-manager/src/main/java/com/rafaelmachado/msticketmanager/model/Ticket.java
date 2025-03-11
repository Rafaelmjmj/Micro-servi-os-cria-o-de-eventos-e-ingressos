package com.rafaelmachado.msticketmanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String eventId; // ID do evento no ms-event-manager
    private String cpf;
    private String name;
    private LocalDateTime purchaseDate;
    private boolean active = true; // Para soft-delete
}