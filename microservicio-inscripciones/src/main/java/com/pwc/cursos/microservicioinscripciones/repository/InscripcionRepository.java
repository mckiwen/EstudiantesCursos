package com.pwc.cursos.microservicioinscripciones.repository;

import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.entity.InscripcionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, InscripcionId> {

    //Optional<Inscripcion> findByidCursoidEstudiante(Long idCurso, Long idEstudiante);
}
