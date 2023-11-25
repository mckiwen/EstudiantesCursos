package com.pwc.cursos.microservicioinscripciones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "inscripciones")
@IdClass(InscripcionId.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Inscripcion {

    @Id
    @Column(name = "id_curso")
    private Long idCurso;

    @Id
    @Column(name = "id_estudiante")
    private Long idEstudiante;
}