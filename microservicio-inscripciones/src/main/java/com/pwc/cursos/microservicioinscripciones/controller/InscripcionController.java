package com.pwc.cursos.microservicioinscripciones.controller;

import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionCursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionesDTO;
import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.entity.InscripcionId;
import com.pwc.cursos.microservicioinscripciones.service.InscripcionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService){
        this.inscripcionService = inscripcionService;
    }

    @GetMapping("/curso/{idCurso}")
    public ResponseEntity<List<Inscripcion>> findAllByIdCurso(@PathVariable Long idCurso){
        List<Inscripcion> listInscripcion = this.inscripcionService.findAllByIdCurso(idCurso);
        return ResponseEntity.ok(listInscripcion);
    }

    @GetMapping("/cursos/{idCurso}")
    public ResponseEntity<InscripcionCursoDTO> findByIdCurso(@PathVariable Long idCurso){
        Optional<InscripcionCursoDTO> inscripcionCursoDTO = this.inscripcionService.findByIdCurso(idCurso);
        return inscripcionCursoDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inscripcion> existsByInscripcion(@RequestBody Inscripcion inscripcion){
        boolean exists = this.inscripcionService.existsByInscripcion(inscripcion);
        if(!exists){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inscripcion);
    }

    @PostMapping("/curso")
    public ResponseEntity<InscripcionesDTO> save(@RequestBody InscripcionesDTO inscripcionesDTO){
        InscripcionesDTO inscripcionesDTOinscritos = this.inscripcionService.save(inscripcionesDTO);
        return ResponseEntity.ok(inscripcionesDTOinscritos);
    }

    @DeleteMapping("/curso")
    public ResponseEntity<InscripcionesDTO> delete(@RequestBody InscripcionesDTO inscripcionesDTO){
        InscripcionesDTO inscripcionesDTObaja = this.inscripcionService.delete(inscripcionesDTO);
        return ResponseEntity.ok(inscripcionesDTObaja);
    }



/*    @PostMapping("/curso")
    public ResponseEntity<Inscripcion> save(@RequestBody Inscripcion inscripcion){
        this.inscripcionService.save(inscripcion);
        return ResponseEntity.ok(inscripcion);
    }*/
}
