package com.pwc.cursos.microservicioinscripciones.controller;

import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.service.InscripcionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
