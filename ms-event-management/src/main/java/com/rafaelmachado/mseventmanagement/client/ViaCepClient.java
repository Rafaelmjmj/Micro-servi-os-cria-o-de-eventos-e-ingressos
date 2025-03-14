package com.rafaelmachado.mseventmanagement.client;



import com.rafaelmachado.mseventmanagement.dto.ViaCepResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ViaCepClient {

    private final RestTemplate restTemplate;
    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public ViaCepResponseDTO buscarEnderecoPorCep(String cep) {
        return restTemplate.getForObject(VIACEP_URL, ViaCepResponseDTO.class, cep);
    }
}

