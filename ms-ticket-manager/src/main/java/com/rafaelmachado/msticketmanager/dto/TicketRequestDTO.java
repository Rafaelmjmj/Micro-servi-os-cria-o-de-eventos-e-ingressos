package com.rafaelmachado.msticketmanager.dto;

public class TicketRequestDTO {
    private String eventId;
    private String cpf;
    private String name;

    public TicketRequestDTO() {}

    public TicketRequestDTO(String eventId, String cpf, String name) {
        this.eventId = eventId;
        this.cpf = cpf;
        this.name = name;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}