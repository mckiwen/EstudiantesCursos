package com.pwc.cursos.microserviciocursos.controller;

import com.pwc.cursos.microserviciocursos.entity.Curso;
import com.pwc.cursos.microserviciocursos.service.CursoService;
import com.pwc.cursos.microserviciocursos.service.CursoServiceImpl;
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
@RequestMapping("/api/cursos")
public class CursoController {

    private static final String EXAMPLE_VALUE_CURSO1_JSON = "{\"id\": 1, \"nombre\": \"SpringBoot\", \"descripcion\": \"Curso de Spring Boot\", \"fechaInicio\": \"2024-01-15\", \"fechaFin\": \"2024-06-15\"}";
    private static final String EXAMPLE_VALUE_CURSO2_JSON = "{\"id\": 2, \"nombre\": \"SpringSecurity\", \"descripcion\": \"Curso de Spring Security\", \"fechaInicio\": \"2024-03-15\", \"fechaFin\": \"2024-09-15\"}";
    private static final String EXAMPLE_VALUE_CURSO3_JSON = "{\"id\": 9, \"nombre\": \"Django\", \"descripcion\": \"Curso de Django\", \"fechaInicio\": \"2024-06-15\", \"fechaFin\": \"2024-12-15\"}";
    private static final String EXAMPLE_VALUE_CURSO_NOID_JSON = "{\"nombre\": \"Flask\", \"descripcion\": \"Curso de Flask\", \"fechaInicio\": \"2024-11-15\", \"fechaFin\": \"2025-06-15\"}";

    private final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);

    private CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    /**
     * Endpoint que obtiene la lista completa de cursos
     * @return ResponseEntity
     */
    @Operation(summary = "Obtiene la lista completa de cursos")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recursos encontrados", content = @Content(examples = {
            @ExampleObject(value = "["+EXAMPLE_VALUE_CURSO1_JSON+", " +EXAMPLE_VALUE_CURSO2_JSON+"]")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: No hay ningún curso", content = @Content(examples = {
            @ExampleObject(value = "[]")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping
    public ResponseEntity<List<Curso>> findAll(){
        List<Curso> listCurso = this.cursoService.findAll();
        log.info("Petición GET para buscar todos los cursos");
        if(listCurso.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(listCurso);
        }
    }

    /**
     * Endpoint que obtiene un curso dado un Id
     * @param id Id de Curso
     * @return Curso
     */
    @Operation(summary = "Obtiene un curso dado un Id")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recurso encontrado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_CURSO1_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id){
        log.info("Petición GET para buscar curso por Id");
        Optional<Curso> cursoOpt = this.cursoService.findById(id);
        if(!cursoService.existsById(id)){
            log.warn("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(cursoOpt.get());
    }

    /**
     * Endpoint que crea un curso mediante una petición POST
     * @param curso Curso
     * @return Curso
     */
    @Operation(summary = "Crea un curso mediante una petición POST")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Curso creado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_CURSO1_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_CURSO_NOID_JSON)))
    @PostMapping
    public ResponseEntity<Curso> create(@RequestBody Curso curso){
        log.info("Petición POST para crear un nuevo curso");
        if(curso.getId() != null){
            log.warn("Petición POST para crear un nuevo curso no debe contener un Id");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.cursoService.save(curso));
    }

    /**
     * Endpoint que actualiza un curso con un método PUT.
     * @param curso Curso
     * @return Curso
     */
    @Operation(summary = "Actualiza un curso con un método PUT.")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Curso actualizado", content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_CURSO1_JSON)))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_CURSO1_JSON)))
    @PutMapping
    public ResponseEntity<Curso> update(@RequestBody Curso curso){
        log.info("Petición PUT para actualizar un curso existente");
        if(curso.getId() == null){
            log.warn("Petición PUT para actualizar un curso existente debe contener un Id");
            return ResponseEntity.badRequest().build();
        }
        if(!cursoService.existsById(curso.getId())){
            log.warn("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.cursoService.save(curso));
    }


    /**
     * Endpoint que elimina un curso dada su Id
     * @param id Id de Curso
     * @return Curso
     */
    @Operation(summary = "Elimina un curso dada su Id")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Curso eliminado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> deleteById(@PathVariable Long id){
        log.info("Petición DETELE para eliminar un curso existente");
        if(!cursoService.existsById(id)){
            log.warn("Recurso no encontrado");
            return ResponseEntity.notFound().build();
        }
        this.cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint que elimina todos los cursos
     * @return
     */
    @Operation(summary = "Elimina todos los cursos")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Cursos eliminados", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @DeleteMapping
    public ResponseEntity<Curso> deleteAll(){
        log.info("Petición DELETE para eliminar todos los cursos");
        this.cursoService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
