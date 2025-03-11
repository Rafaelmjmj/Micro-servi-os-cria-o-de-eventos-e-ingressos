package com.rafaelmachado.msticketmanager.dto;

import java.time.LocalDateTime;
import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private String id;
    private String eventId;
    private String cpf;
    private String name;
    private LocalDateTime purchaseDate;
    private boolean active;
}
