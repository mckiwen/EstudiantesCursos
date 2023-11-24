package com.pwc.cursos.microserviciocursos.service;

import com.pwc.cursos.microserviciocursos.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> findAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);

    void delete(Long id);

    void deleteAll();
}
