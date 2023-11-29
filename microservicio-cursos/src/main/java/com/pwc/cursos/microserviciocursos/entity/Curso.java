package com.pwc.cursos.microserviciocursos.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @Nonnull
    private String nombre;

    @Schema(description = "Descripcion del curso", example = "Curso de Spring Boot")
    @Nonnull
    private String descripcion;

    @Schema(description = "Fecha de inicio", example = "2024-01-15")
    @Nonnull
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de finalizacion", example = "2024-06-15")
    @Nonnull
    private LocalDate fechaFin;
}
