package com.pwc.cursos.microservicioinscripciones.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class InscripcionesDTO {

    private Long idCurso;
    private List<Long> idEstudianteList;
}
