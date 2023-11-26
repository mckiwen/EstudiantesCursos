package com.pwc.cursos.microserviciocursos.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del Curso", example = "SpringBoot")
    private String nombre;

    @Schema(description = "Descripcion del curso", example = "Curso de Spring Boot")
    private String descripcion;

    @Schema(description = "Fecha de inicio", example = "2024-01-15")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de finalizacion", example = "2024-06-15")
    private LocalDate fechaFin;
}
