package com.rafaelmachado.mseventmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data                     // Gera getters, setters, toString, equals e hashCode
@NoArgsConstructor        // Cria um construtor sem argumentos
@AllArgsConstructor       // Cria um construtor com todos os argumentos
@Document(collection = "events")
public class Event {

    @Id
    private String id;
    private String eventName;
    private LocalDateTime dateTime;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}
