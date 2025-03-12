package com.rafaelmachado.mseventmanagement.dto;

import lombok.Data;

@Data
public class ViaCepResponseDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade; // Cidade
    private String uf; // Estado
}