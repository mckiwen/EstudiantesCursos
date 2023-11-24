package com.pwc.estudiantes.microservicioestudiantes.service;

import com.pwc.estudiantes.microservicioestudiantes.entity.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteService {
    List<Estudiante> findAll();

    Optional<Estudiante> findById(Long id);

    Estudiante save(Estudiante curso);

    void delete(Long id);

    void deleteAll();

    boolean existsById(Long id);
}
