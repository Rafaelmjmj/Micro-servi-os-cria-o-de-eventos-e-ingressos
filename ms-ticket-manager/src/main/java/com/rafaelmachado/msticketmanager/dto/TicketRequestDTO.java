package com.rafaelmachado.msticketmanager.dto;
import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    private String eventId;
    private String cpf;
    private String name;
}
