package com.pwc.cursos.microservicioinscripciones.service;

import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;

import java.util.List;

public interface InscripcionService {

    List<Inscripcion> findAllByIdCurso(Long idCurso);
}
