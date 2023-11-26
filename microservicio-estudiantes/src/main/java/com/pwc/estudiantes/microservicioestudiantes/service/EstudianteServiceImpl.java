package com.pwc.estudiantes.microservicioestudiantes.service;

import com.pwc.estudiantes.microservicioestudiantes.entity.Estudiante;
import com.pwc.estudiantes.microservicioestudiantes.repository.EstudianteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService{

    private final Logger log = LoggerFactory.getLogger(EstudianteServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    private EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }


    @Override
    public List<Estudiante> findAll() {
        log.info("Ejecutando método de listar todos los estudiantes");
        return this.estudianteRepository.findAll();
    }

    @Override
    public Optional<Estudiante> findById(Long id) {
        log.info("Ejecutando método buscar estudiante por Id");
        return this.estudianteRepository.findById(id);
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        log.info("Ejecutando método para guardar un estudiante");
        return this.estudianteRepository.save(estudiante);
    }

    @Override
    public void delete(Long id) {
        log.info("Ejecutando método para eliminar un estudiante");
        restTemplate.delete("http://localhost:8082/api/inscripciones/estudiante/"+id);
        this.estudianteRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        log.info("Ejecutando método para eliminar todos los estudiantes");
        this.estudianteRepository.deleteAll();
    }

    @Override
    public boolean existsById(Long id) {
        return this.estudianteRepository.existsById(id);
    }
}
