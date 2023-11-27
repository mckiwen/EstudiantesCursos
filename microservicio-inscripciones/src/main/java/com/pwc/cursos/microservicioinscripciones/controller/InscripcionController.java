package com.pwc.cursos.microservicioinscripciones.controller;

import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionCursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionesDTO;
import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.entity.InscripcionId;
import com.pwc.cursos.microservicioinscripciones.service.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private static final String EXAMPLE_VALUE_CURSO1_JSON =
            "{\"id\": 1, \"nombre\": \"SpringBoot\", \"descripcion\": \"Curso de Spring Boot\", \"fechaInicio\":" +
                    " \"2024-01-15\", \"fechaFin\": \"2024-06-15\", \"listEstudiante\": [" +
                    "{\"id\": 1, \"nombre\": \"Antonio\", \"apellido\": \"Gomez\", \"fechaNacimiento\": \"1991-04-22\"}]}";

    private static final String EXAMPLE_VALUE_INSCRIPCIONES_JSON =
            "{\"idCurso\": 1, \"idEstudianteList\": [ 1, 2, 3]}";

    private InscripcionService inscripcionService;


    public InscripcionController(InscripcionService inscripcionService){
        this.inscripcionService = inscripcionService;
    }

    /**
     * EndPoint que obtiene un Curso dado un Id de Curso con los estudiantes inscritos.
     * @param idCurso Id de Curso
     * @return InscripcionCursoDTO
     */
    @Operation(summary = "EndPoint que obtiene un Curso dado un Id con los estudiantes inscritos.")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recurso encontrado", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_CURSO1_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<InscripcionCursoDTO> findByIdCurso(@PathVariable Long idCurso){
        Optional<InscripcionCursoDTO> inscripcionCursoDTO = this.inscripcionService.findByIdCurso(idCurso);
        return inscripcionCursoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Endpoint que inscribe un grupo de estudiantes mediante una petición POST
     * @param inscripcionesDTO InscripcionesDTO
     * @return InscripcionesDTO
     */
    @Operation(summary = "Inscribe un grupo de estudiantes mediante una petición POST")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Estudiantes inscritos", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_INSCRIPCIONES_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_INSCRIPCIONES_JSON)))
    @PostMapping("/curso")
    public ResponseEntity<InscripcionesDTO> save(@RequestBody InscripcionesDTO inscripcionesDTO){
        InscripcionesDTO inscripcionesDTOinscritos = this.inscripcionService.save(inscripcionesDTO);
        return ResponseEntity.ok(inscripcionesDTOinscritos);
    }

    /**
     * Endpoint que desinscribe un grupo de estudiantes mediante una petición DELETE
     * @param inscripcionesDTO InscripcionesDTO
     * @return InscripcionesDTO
     */
    @Operation(summary = "Desinscribe un grupo de estudiantes mediante una petición POST")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Estudiantes desinscritos", content = @Content(examples = {
            @ExampleObject(value = EXAMPLE_VALUE_INSCRIPCIONES_JSON)
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400", description = "Petición errónea", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            examples = @ExampleObject(name = "Ejemplo de Solicitud", value = EXAMPLE_VALUE_INSCRIPCIONES_JSON)))
    @DeleteMapping("/curso")
    public ResponseEntity<InscripcionesDTO> delete(@RequestBody InscripcionesDTO inscripcionesDTO){
        InscripcionesDTO inscripcionesDTObaja = this.inscripcionService.delete(inscripcionesDTO);
        return ResponseEntity.ok(inscripcionesDTObaja);
    }

    /**
     * Endpoint que elimina todas las inscripciones de un curso dado su IdCurso
     * @param idCurso IdCurso del curso
     * @return
     */
    @Operation(summary = "Elimina todas las inscripciones de un curso dado su IdCurso")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Curso eliminado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @DeleteMapping("/curso/{idCurso}")
    public ResponseEntity<Long> deleteCurso(@PathVariable Long idCurso){
        this.inscripcionService.deleteByIdCurso(idCurso);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint que elimina todas las inscripciones de un estudiante dado su IdEstudiante
     * @param idEstudiante IdEstudiante del estudiante
     * @return
     */
    @Operation(summary = "Elimina todas las inscripciones de un estudiante dado su IdEstudiante")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Estudiante eliminado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @DeleteMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<Long> deleteEstudiante(@PathVariable Long idEstudiante){
        this.inscripcionService.deleteByIdEstudiante(idEstudiante);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint que elimina todas las inscripciones.
     * @return
     */
    @Operation(summary = "Elimina todas las inscripciones.")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Inscripciones eliminadas", content = @Content(examples = {
            @ExampleObject(value = "")}))
    @DeleteMapping
    public ResponseEntity<Inscripcion> deleteAll(){
        this.inscripcionService.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
