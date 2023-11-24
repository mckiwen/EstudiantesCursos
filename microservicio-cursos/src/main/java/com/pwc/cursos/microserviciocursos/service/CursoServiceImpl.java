package com.pwc.cursos.microserviciocursos.service;

import com.pwc.cursos.microserviciocursos.entity.Curso;
import com.pwc.cursos.microserviciocursos.repository.CursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    private final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);

    private CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    @Override
    public List<Curso> findAll() {
        log.info("Ejecutando listar todos los cursos");
        return this.cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Curso save(Curso curso) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
