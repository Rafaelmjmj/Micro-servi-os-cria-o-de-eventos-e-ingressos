package com.rafaelmachado.msticketmanager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String eventId;
    private String cpf;
    private String name;
    private LocalDateTime purchaseDate;
    private boolean active;

    public Ticket() {}

    public Ticket(String id, String eventId, String cpf, String name, LocalDateTime purchaseDate, boolean active) {
        this.id = id;
        this.eventId = eventId;
        this.cpf = cpf;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.active = active;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}