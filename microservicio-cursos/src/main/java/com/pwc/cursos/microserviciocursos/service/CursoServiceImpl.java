package com.pwc.cursos.microserviciocursos.service;

import com.pwc.cursos.microserviciocursos.entity.Curso;
import com.pwc.cursos.microserviciocursos.repository.CursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Value("${inscripciones-url}")
    private String hostInscripciones;

    private final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository){
        this.cursoRepository = cursoRepository;
    }

    @Override
    public List<Curso> findAll() {
        log.info("Ejecutando método de listar todos los cursos");
        return this.cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        log.info("Ejecutando método buscar curso por Id");
        return this.cursoRepository.findById(id);
    }

    @Override
    public Curso save(Curso curso) {
        log.info("Ejecutando método para guardar un curso");
        return this.cursoRepository.save(curso);
    }

    @Override
    public void delete(Long id) {
        log.info("Ejecutando método para eliminar un curso");
        try{
            restTemplate.delete("http://" + hostInscripciones +":8082/api/inscripciones/curso/"+id);
        } catch (Exception e){
            log.warn(e.getMessage());
        }
        this.cursoRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        log.info("Ejecutando método para eliminar todos los cursos");
        restTemplate.delete("http://" + hostInscripciones + ":8082/api/inscripciones");
        this.cursoRepository.deleteAll();
    }

    @Override
    public boolean existsById(Long id) {
        return this.cursoRepository.existsById(id);
    }
}
