package com.pwc.cursos.microservicioinscripciones.repository;

import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.entity.InscripcionId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, InscripcionId> {
    List<Inscripcion> findAllByIdCurso(Long idCurso);

    boolean existsByIdCurso(Long idCurso);

    boolean existsByIdCursoAndIdEstudiante(Long idCurso, Long idEstudiante);
    @Modifying
    @Transactional
    @Query("DELETE FROM Inscripcion ins WHERE ins.idEstudiante = :idEstudiante")
    void deleteAllByIdEstudiante(Long idEstudiante);

    @Modifying
    @Transactional
    @Query("DELETE FROM Inscripcion ins WHERE ins.idCurso = :idCurso")
    void deleteAllByIdCurso(Long idCurso);

}
