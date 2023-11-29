package com.pwc.cursos.microserviciocursos.controller;

import com.pwc.cursos.microserviciocursos.entity.Curso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ICursoController {

    String EXAMPLE_VALUE_CURSO1_JSON = "{\"id\": 1, \"nombre\": \"SpringBoot\", \"descripcion\": \"Curso de Spring Boot\", \"fechaInicio\": \"2024-01-15\", \"fechaFin\": \"2024-06-15\"}";
    String EXAMPLE_VALUE_CURSO2_JSON = "{\"id\": 2, \"nombre\": \"SpringSecurity\", \"descripcion\": \"Curso de Spring Security\", \"fechaInicio\": \"2024-03-15\", \"fechaFin\": \"2024-09-15\"}";
    String EXAMPLE_VALUE_CURSO3_JSON = "{\"id\": 9, \"nombre\": \"Django\", \"descripcion\": \"Curso de Django\", \"fechaInicio\": \"2024-06-15\", \"fechaFin\": \"2024-12-15\"}";
    String EXAMPLE_VALUE_CURSO_NOID_JSON = "{\"nombre\": \"Flask\", \"descripcion\": \"Curso de Flask\", \"fechaInicio\": \"2024-11-15\", \"fechaFin\": \"2025-06-15\"}";


    @Operation(summary = "Obtiene la lista completa de cursos")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recursos encontrados", content = @Content(examples = {
            @ExampleObject(value = "["+EXAMPLE_VALUE_CURSO1_JSON+", " +EXAMPLE_VALUE_CURSO2_JSON+"]")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: No hay ningún curso", content = @Content(examples = {
            @ExampleObject(value = "[]")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<List<Curso>> findAll();


    @Operation(summary = "Obtiene un curso dado un Id")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recurso encontrado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_CURSO1_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Curso> findById(@PathVariable Long id);

    @Operation(summary = "Crea un curso mediante una petición POST")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Curso creado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_CURSO3_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_CURSO_NOID_JSON)))
    public ResponseEntity<Curso> create(@RequestBody Curso curso);

    @Operation(summary = "Actualiza un curso con un método PUT.")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Curso actualizado", content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_CURSO1_JSON)))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_CURSO1_JSON)))
    public ResponseEntity<Curso> update(@RequestBody Curso curso);

    @Operation(summary = "Elimina un curso dada su Id")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Curso eliminado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Curso> deleteById(@PathVariable Long id);

    @Operation(summary = "Elimina todos los cursos")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Cursos eliminados", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Curso> deleteAll();
}
