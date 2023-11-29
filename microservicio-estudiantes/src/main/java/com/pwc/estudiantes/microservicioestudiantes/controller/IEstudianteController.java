package com.pwc.estudiantes.microservicioestudiantes.controller;

import com.pwc.estudiantes.microservicioestudiantes.entity.Estudiante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IEstudianteController {

    String EXAMPLE_VALUE_ESTUDIANTE1_JSON = "{\"id\": 1, \"nombre\": \"Antonio\", \"apellido\": \"Gomez\", \"fechaNacimiento\": \"1991-04-22\"}";
    String EXAMPLE_VALUE_ESTUDIANTE2_JSON = "{\"id\": 2, \"nombre\": \"Maria\", \"apellido\": \"Marquez\", \"fechaNacimiento\": \"1995-08-17\"}";
    String EXAMPLE_VALUE_ESTUDIANTE3_JSON = "{\"id\": 9, \"nombre\": \"Antonio\", \"apellido\": \"Gomez\", \"fechaNacimiento\": \"1991-04-22\"}";
    String EXAMPLE_VALUE_ESTUDIANTE_NOID_JSON = "{\"nombre\": \"Antonio\", \"apellido\": \"Gomez\", \"fechaNacimiento\": \"1991-04-22\"}";

    @Operation(summary = "Obtiene la lista completa de estudiantes")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recursos encontrados", content = @Content(examples = {
            @ExampleObject(value = "["+EXAMPLE_VALUE_ESTUDIANTE1_JSON +", " +EXAMPLE_VALUE_ESTUDIANTE2_JSON+"]")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: No hay ningún estudiante", content = @Content(examples = {
            @ExampleObject(value = "[]")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    public ResponseEntity<List<Estudiante>> findAll();

    @Operation(summary = "Obtiene un estudiante dado un Id")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recurso encontrado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_ESTUDIANTE1_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Estudiante> findById(@PathVariable Long id);

    @Operation(summary = "Registra un estudiante mediante una petición POST")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Estudiante registrado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_ESTUDIANTE3_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_ESTUDIANTE_NOID_JSON)))
    public ResponseEntity<Estudiante> create(@RequestBody Estudiante estudiante);

    @Operation(summary = "Actualiza los datos de un estudiante con un método PUT.")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Estudiante actualizado", content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_ESTUDIANTE1_JSON)))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_ESTUDIANTE1_JSON)))
    public ResponseEntity<Estudiante> update(@RequestBody Estudiante estudiante);


    @Operation(summary = "Elimina a un estudiante dado su Id")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Estudiante eliminado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Estudiante> deleteById(@PathVariable Long id);


    @Operation(summary = "Elimina todos los estudiantes")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Estudiantes eliminados", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Estudiante> deleteAll();


}
