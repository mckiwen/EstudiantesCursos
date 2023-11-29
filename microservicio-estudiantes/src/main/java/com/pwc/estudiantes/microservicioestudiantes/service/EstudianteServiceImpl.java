package com.pwc.estudiantes.microservicioestudiantes.service;

import com.pwc.estudiantes.microservicioestudiantes.entity.Estudiante;
import com.pwc.estudiantes.microservicioestudiantes.repository.EstudianteRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService{

    @Value("${cursos-url}")
    private String hostCursos;

    @Value("${inscripciones-url}")
    private String hostInscripciones;

    private final Logger log = LoggerFactory.getLogger(EstudianteServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }


    /**
     * Metodo para obtener la lista de Estudiantes
     * @return List
     */
    @Override
    public List<Estudiante> findAll() {
        log.info("Ejecutando método de listar todos los estudiantes");
        return this.estudianteRepository.findAll();
    }

    /**
     * Metodo que busca a un Estudiante dado su Id
     * @param id Id de Estudiante
     * @return Optional
     */
    @Override
    public Optional<Estudiante> findById(Long id) {
        log.info("Ejecutando método buscar estudiante por Id");
        return this.estudianteRepository.findById(id);
    }

    /**
     * Metodo para guardar un Estudiante en el repositorio
     * @param estudiante Estudiante
     * @return Estudiante
     */
    @Override
    @Transactional
    public Estudiante save(Estudiante estudiante) {
        log.info("Ejecutando método para guardar un estudiante");
        return this.estudianteRepository.save(estudiante);
    }

    /**
     * Metodo para eliminar a un Estudiante del repositorio dado su Id.
     * @param id Id de Estudiante
     */
    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Ejecutando método para eliminar un estudiante");
        try{
            restTemplate.delete("http://" + hostInscripciones + ":8082/api/inscripciones/estudiante/"+id);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        this.estudianteRepository.deleteById(id);
    }

    /**
     * Metodo para eliminar todos los Estudiantes del repositorio.
     */
    @Override
    @Transactional
    public void deleteAll() {
        log.info("Ejecutando método para eliminar todos los estudiantes");
        try{
            restTemplate.delete("http://" + hostInscripciones + ":8082/api/inscripciones");
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        this.estudianteRepository.deleteAll();
    }

    /**
     * Metodo para verificar si existe un registro de un Estudiante dado su Id.
     * @param id Id de Estudiante
     * @return boolean
     */
    @Override
    public boolean existsById(Long id) {
        return this.estudianteRepository.existsById(id);
    }
}
