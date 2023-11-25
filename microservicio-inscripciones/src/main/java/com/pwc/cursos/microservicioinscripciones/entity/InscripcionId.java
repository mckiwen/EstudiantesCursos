package com.pwc.cursos.microservicioinscripciones.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InscripcionId implements Serializable {

    private Long idCurso;
    private Long idEstudiante;
}
