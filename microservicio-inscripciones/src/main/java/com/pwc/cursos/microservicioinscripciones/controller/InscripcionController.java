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
public class InscripcionController implements IInscripcionController{


    private InscripcionService inscripcionService;


    public InscripcionController(InscripcionService inscripcionService){
        this.inscripcionService = inscripcionService;
    }

    /**
     * EndPoint que obtiene un Curso dado un Id de Curso con los estudiantes inscritos.
     * @param idCurso Id de Curso
     * @return InscripcionCursoDTO
     */
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
    @DeleteMapping("/estudiante/{idEstudiante}")
    public ResponseEntity<Long> deleteEstudiante(@PathVariable Long idEstudiante){
        this.inscripcionService.deleteByIdEstudiante(idEstudiante);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint que elimina todas las inscripciones.
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Inscripcion> deleteAll(){
        this.inscripcionService.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
