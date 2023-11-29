package com.pwc.cursos.microservicioinscripciones.controller;

import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionCursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionesDTO;
import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IInscripcionController {

    String EXAMPLE_VALUE_CURSO1_JSON =
            "{\"id\": 1, \"nombre\": \"SpringBoot\", \"descripcion\": \"Curso de Spring Boot\", \"fechaInicio\":" +
                    " \"2024-01-15\", \"fechaFin\": \"2024-06-15\", \"listEstudiante\": [" +
                    "{\"id\": 1, \"nombre\": \"Antonio\", \"apellido\": \"Gomez\", \"fechaNacimiento\": \"1991-04-22\"}]}";

    String EXAMPLE_VALUE_INSCRIPCIONES_JSON =
            "{\"idCurso\": 1, \"idEstudianteList\": [ 1, 2, 3]}";


    @Operation(summary = "EndPoint que obtiene un Curso dado un Id con los estudiantes inscritos.")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recurso encontrado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_CURSO1_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<InscripcionCursoDTO> findByIdCurso(@PathVariable Long idCurso);

    @Operation(summary = "Inscribe un grupo de estudiantes mediante una petición POST")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Estudiantes inscritos", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_INSCRIPCIONES_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_INSCRIPCIONES_JSON)))
    public ResponseEntity<InscripcionesDTO> save(@RequestBody InscripcionesDTO inscripcionesDTO);

    @Operation(summary = "Desinscribe un grupo de estudiantes mediante una petición DELETE")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Estudiantes desinscritos", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_INSCRIPCIONES_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_INSCRIPCIONES_JSON)))
    public ResponseEntity<InscripcionesDTO> delete(@RequestBody InscripcionesDTO inscripcionesDTO);

    @Operation(summary = "Elimina todas las inscripciones de un curso dado su IdCurso")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Curso eliminado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Long> deleteCurso(@PathVariable Long idCurso);

    @Operation(summary = "Elimina todas las inscripciones de un estudiante dado su IdEstudiante")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Estudiante eliminado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Long> deleteEstudiante(@PathVariable Long idEstudiante);

    @Operation(summary = "Elimina todas las inscripciones.")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Inscripciones eliminadas", content = @Content(examples = {
            @ExampleObject(value = "")}))
    public ResponseEntity<Inscripcion> deleteAll();

}
