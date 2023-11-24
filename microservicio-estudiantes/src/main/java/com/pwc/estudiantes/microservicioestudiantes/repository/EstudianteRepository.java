package com.pwc.estudiantes.microservicioestudiantes.repository;

import com.pwc.estudiantes.microservicioestudiantes.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
