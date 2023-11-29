package com.pwc.cursos.microserviciocursos.service;

import com.pwc.cursos.microserviciocursos.entity.Curso;
import com.pwc.cursos.microserviciocursos.repository.CursoRepository;
import jakarta.transaction.Transactional;
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

    /**
     * Metodo para obtener la lista de Cursos
     * @return List
     */
    @Override
    public List<Curso> findAll() {
        log.info("Ejecutando método de listar todos los cursos");
        return this.cursoRepository.findAll();
    }

    /**
     * Metodo que busca un Curso dado su Id
     * @param id Id de Curso
     * @return Optional
     */
    @Override
    public Optional<Curso> findById(Long id) {
        log.info("Ejecutando método buscar curso por Id");
        return this.cursoRepository.findById(id);
    }

    /**
     * Metodo para guardar un Curso en el repositorio
     * @param curso Curso
     * @return Curso
     */
    @Override
    @Transactional
    public Curso save(Curso curso) {
        log.info("Ejecutando método para guardar un curso");
        return this.cursoRepository.save(curso);
    }

    /**
     * Metodo para eliminar un Curso del repositorio dado su Id.
     * @param id Id de Curso
     */
    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Ejecutando método para eliminar un curso");
        try{
            restTemplate.delete("http://" + hostInscripciones +":8082/api/inscripciones/curso/"+id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        this.cursoRepository.deleteById(id);
    }

    /**
     * Metodo para eliminar todos los Cursos del repositorio.
     */
    @Override
    @Transactional
    public void deleteAll() {
        log.info("Ejecutando método para eliminar todos los cursos");
        try{
            restTemplate.delete("http://" + hostInscripciones + ":8082/api/inscripciones");
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        this.cursoRepository.deleteAll();
    }

    /**
     * Metodo para verificar si existe un registro de un Curso dado su Id.
     * @param id Id de Curso
     * @return boolean
     */
    @Override
    public boolean existsById(Long id) {
        return this.cursoRepository.existsById(id);
    }
}
