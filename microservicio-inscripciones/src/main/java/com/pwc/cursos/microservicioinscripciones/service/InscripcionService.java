package com.pwc.cursos.microservicioinscripciones.service;

import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionCursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionesDTO;
import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.entity.InscripcionId;

import java.util.List;
import java.util.Optional;

public interface InscripcionService {

    List<Inscripcion> findAllByIdCurso(Long idCurso);

    Optional<InscripcionCursoDTO> findByIdCurso(Long idCurso);

    boolean existsByInscripcion(Inscripcion inscripcion);


    InscripcionesDTO save(InscripcionesDTO inscripcionesDTO);

    InscripcionesDTO delete(InscripcionesDTO inscripcionesDTO);

    void deleteByIdEstudiante(Long idEstudiante);
}
