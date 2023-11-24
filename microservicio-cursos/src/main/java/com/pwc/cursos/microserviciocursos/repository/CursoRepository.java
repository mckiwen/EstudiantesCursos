package com.pwc.cursos.microserviciocursos.repository;

import com.pwc.cursos.microserviciocursos.entity.Curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
