package com.pwc.estudiantes.microservicioestudiantes.controller;

import com.pwc.estudiantes.microservicioestudiantes.entity.Estudiante;
import com.pwc.estudiantes.microservicioestudiantes.service.EstudianteService;
import com.pwc.estudiantes.microservicioestudiantes.service.EstudianteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController implements IEstudianteController{


    private final Logger log = LoggerFactory.getLogger(EstudianteServiceImpl.class);

    private EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService){
        this.estudianteService = estudianteService;
    }

    /**
     * Endpoint que obtiene la lista completa de estudiantes
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Estudiante>> findAll(){
        List<Estudiante> listEstudiante = this.estudianteService.findAll();
        log.info("Petición GET para buscar todos los estudiantes");
        if(listEstudiante.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(listEstudiante);
        }
    }

    /**
     * EndPoint que obtiene un Estudiante dado un Id.
     * @param id Id de Estudiante
     * @return Estudiante
     */
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> findById(@PathVariable Long id){
        log.info("Petición GET para buscar un estudiante por Id");
        Optional<Estudiante> estudianteOpt = this.estudianteService.findById(id);
        if(!estudianteService.existsById(id)){
            log.error("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(estudianteOpt.get());
    }

    /**
     * Endpoint que registra un estudiante mediante una petición POST
     * @param estudiante Estudiante
     * @return Estudiante
     */
    @PostMapping
    public ResponseEntity<Estudiante> create(@RequestBody Estudiante estudiante){
        log.info("Petición POST para registrar un nuevo estudiante");
        if(estudiante.getId() != null){
            log.error("Petición POST para registrar un nuevo estudiante no debe contener un Id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.estudianteService.save(estudiante));
    }

    /**
     * Endpoint que actualiza a un estudiante con un método PUT.
     * @param estudiante Estudiante
     * @return Estudiante
     */
    @PutMapping
    public ResponseEntity<Estudiante> update(@RequestBody Estudiante estudiante){
        log.info("Petición PUT para actualizar los datos de un estudiante existente");
        if(estudiante.getId() == null){
            log.error("Petición PUT para actualizar los datos de un estudiante existente debe contener un Id");
            return ResponseEntity.badRequest().build();
        }
        if(!estudianteService.existsById(estudiante.getId())){
            log.error("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.estudianteService.save(estudiante));
    }


    /**
     * Endpoint que elimina a un estudiante dado su Id
     * @param id Id de Estudiante
     * @return Estudiante
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Estudiante> deleteById(@PathVariable Long id){
        log.info("Petición DELETE para eliminar a un estudiante existente");
        if(!estudianteService.existsById(id)){
            log.error("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        this.estudianteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint que elimina todos los estudiantes
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Estudiante> deleteAll(){
        log.info("Petición DELETE para eliminar todos los estudiantes");
        this.estudianteService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
