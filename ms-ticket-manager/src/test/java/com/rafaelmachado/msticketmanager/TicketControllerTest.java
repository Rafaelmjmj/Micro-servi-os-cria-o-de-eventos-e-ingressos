package com.rafaelmachado.msticketmanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelmachado.msticketmanager.controller.TicketController;
import com.rafaelmachado.msticketmanager.dto.TicketRequestDTO;
import com.rafaelmachado.msticketmanager.dto.TicketResponseDTO;
import com.rafaelmachado.msticketmanager.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    void testCreateTicket() throws Exception {
        TicketRequestDTO request = new TicketRequestDTO("event123", "12345678900", "Rafael");
        TicketResponseDTO response = new TicketResponseDTO("ticket123", "event123", "12345678900", "Rafael", LocalDateTime.now(), true);

        when(ticketService.createTicket(any(TicketRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/tickets/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("ticket123"));
    }

    @Test
    void testGetTicket() throws Exception {
        TicketResponseDTO response = new TicketResponseDTO("ticket123", "event123", "12345678900", "Rafael", LocalDateTime.now(), true);

        when(ticketService.getTicketById("ticket123")).thenReturn(response);

        mockMvc.perform(get("/tickets/get-ticket/ticket123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ticket123"));
    }

    @Test
    void testGetTicketsByCpf() throws Exception {
        TicketResponseDTO response = new TicketResponseDTO("ticket123", "event123", "12345678900", "Rafael", LocalDateTime.now(), true);
        List<TicketResponseDTO> responseList = Collections.singletonList(response);

        when(ticketService.getTicketsByCpf("12345678900")).thenReturn(responseList);

        mockMvc.perform(get("/tickets/get-ticket-by-cpf/12345678900"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testCancelTicket() throws Exception {
        mockMvc.perform(delete("/tickets/cancel-ticket/ticket123"))
                .andExpect(status().isNoContent());
    }
}
