package com.pwc.cursos.microserviciocursos.controller;

import com.pwc.cursos.microserviciocursos.entity.Curso;
import com.pwc.cursos.microserviciocursos.service.CursoService;
import jakarta.persistence.GeneratedValue;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> findAll(){
        List<Curso> listCurso = this.cursoService.findAll();

        if(listCurso.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(listCurso);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id){
        Optional<Curso> cursoOpt = this.cursoService.findById(id);
        return cursoOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> create(@RequestBody Curso curso){
        if(curso.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.cursoService.save(curso));
    }

    @PutMapping
    public ResponseEntity<Curso> update(@RequestBody Curso curso){
        if(curso.getId() == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.cursoService.save(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> deleteById(@PathVariable Long id){
        this.cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Curso> deleteAll(){
        this.cursoService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
