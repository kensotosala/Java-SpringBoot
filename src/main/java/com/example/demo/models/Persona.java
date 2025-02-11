package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column
    @Getter
    @Setter
    private String nombre;

    @Column(name = "Teléfono") // Cambiar el nombre de cómo aparece en la base de datos
    @Getter // Se usa Lombok para evitar crear más codigo con los Getters y Setters
    @Setter
    private String telefono;
}
