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
public class EstudianteController {

    private static final String EXAMPLE_VALUE_ESTUDIANTE1_JSON = "{\"id\": 1, \"nombre\": \"Antonio\", \"apellido\": \"Gomez\", \"fechaNacimiento\": \"1991-04-22\"}";
    private static final String EXAMPLE_VALUE_ESTUDIANTE2_JSON = "{\"id\": 2, \"nombre\": \"Maria\", \"apellido\": \"Marquez\", \"fechaNacimiento\": \"1995-08-17\"}";
    private static final String EXAMPLE_VALUE_ESTUDIANTE3_JSON = "{\"id\": 9, \"nombre\": \"Antonio\", \"apellido\": \"Gomez\", \"fechaNacimiento\": \"1991-04-22\"}";
    private static final String EXAMPLE_VALUE_ESTUDIANTE_NOID_JSON = "{\"nombre\": \"Antonio\", \"apellido\": \"Gomez\", \"fechaNacimiento\": \"1991-04-22\"}";

    private final Logger log = LoggerFactory.getLogger(EstudianteServiceImpl.class);

    private EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService){
        this.estudianteService = estudianteService;
    }

    /**
     * Endpoint que obtiene la lista completa de estudiantes
     * @return ResponseEntity
     */
    @Operation(summary = "Obtiene la lista completa de estudiantes")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recursos encontrados", content = @Content(examples = {
            @ExampleObject(value = "["+EXAMPLE_VALUE_ESTUDIANTE1_JSON +", " +EXAMPLE_VALUE_ESTUDIANTE2_JSON+"]")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: No hay ningún estudiante", content = @Content(examples = {
            @ExampleObject(value = "[]")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
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
    @Operation(summary = "Obtiene un estudiante dado un Id")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recurso encontrado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_ESTUDIANTE1_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")
    }))
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> findById(@PathVariable Long id){
        log.info("Petición GET para buscar un estudiante por Id");
        Optional<Estudiante> estudianteOpt = this.estudianteService.findById(id);
        if(!estudianteService.existsById(id)){
            log.warn("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(estudianteOpt.get());
    }

    /**
     * Endpoint que registra un estudiante mediante una petición POST
     * @param estudiante Estudiante
     * @return Estudiante
     */
    @Operation(summary = "Registra un estudiante mediante una petición POST")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Estudiante registrado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_ESTUDIANTE3_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_ESTUDIANTE_NOID_JSON)))
    @PostMapping
    public ResponseEntity<Estudiante> create(@RequestBody Estudiante estudiante){
        log.info("Petición POST para registrar un nuevo estudiante");
        if(estudiante.getId() != null){
            log.warn("Petición POST para registrar un nuevo estudiante no debe contener un Id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.estudianteService.save(estudiante));
    }

    /**
     * Endpoint que actualiza a un estudiante con un método PUT.
     * @param estudiante Estudiante
     * @return Estudiante
     */
    @Operation(summary = "Actualiza los datos de un estudiante con un método PUT.")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Estudiante actualizado")
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_ESTUDIANTE1_JSON)))
    @PutMapping
    public ResponseEntity<Estudiante> update(@RequestBody Estudiante estudiante){
        log.info("Petición PUT para actualizar los datos de un estudiante existente");
        if(estudiante.getId() == null){
            log.warn("Petición PUT para actualizar los datos de un estudiante existente debe contener un Id");
            return ResponseEntity.badRequest().build();
        }
        if(!estudianteService.existsById(estudiante.getId())){
            log.warn("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.estudianteService.save(estudiante));
    }


    /**
     * Endpoint que elimina a un estudiante dado su Id
     * @param id Id de Estudiante
     * @return Estudiante
     */
    @Operation(summary = "Elimina a un estudiante dado su Id")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Estudiante eliminado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @DeleteMapping("/{id}")
    public ResponseEntity<Estudiante> deleteById(@PathVariable Long id){
        log.info("Petición DELETE para eliminar a un estudiante existente");
        if(!estudianteService.existsById(id)){
            log.warn("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        this.estudianteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint que elimina todos los estudiantes
     * @return
     */
    @Operation(summary = "Elimina todos los estudiantes")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Estudiantes eliminados", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @DeleteMapping
    public ResponseEntity<Estudiante> deleteAll(){
        log.info("Petición DELETE para eliminar todos los estudiantes");
        this.estudianteService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
