package com.pwc.cursos.microservicioinscripciones.service;

import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionCursoDTO;
import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;

import java.util.List;
import java.util.Optional;

public interface InscripcionService {

    List<Inscripcion> findAllByIdCurso(Long idCurso);

    Optional<InscripcionCursoDTO> findByIdCurso(Long idCurso);
}
