package com.pwc.estudiantes.microservicioestudiantes.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "estudiantes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del Estudiante", example = "John")
    private String nombre;

    @Schema(description = "Apellido del Estudiante", example = "Doe")
    private String apellido;

    @Schema(description = "Fecha de nacimiento del estudiante", example = "1991-09-27")
    private LocalDate fechaNacimiento;
}
