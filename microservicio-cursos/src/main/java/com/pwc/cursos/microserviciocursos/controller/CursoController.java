package com.pwc.cursos.microserviciocursos.controller;

import com.pwc.cursos.microserviciocursos.entity.Curso;
import com.pwc.cursos.microserviciocursos.service.CursoService;
import com.pwc.cursos.microserviciocursos.service.CursoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);

    private CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    /**
     * Obtiene la lista completa de cursos
     * @return ResponseEntity
     */
    @Operation(summary = "Obtiene la lista completa de cursos")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recursos encontrados")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: No hay ningún curso")
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
     * Obtiene un curso dado un Id
     * @param id
     * @return
     */
    @Operation(summary = "Obtiene un curso dado un Id")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Recurso encontrado")
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
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
     * Crea un curso mediante una petición POST
     * @param curso
     * @return
     */
    @Operation(summary = "Crea un curso mediante una petición POST")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Curso creado")
    @ApiResponse(responseCode = "400", description = "Petición errónea")
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
     * Actualiza un curso con un método PUT.
     * @param curso
     * @return
     */
    @Operation(summary = "Actualiza un curso con un método PUT.")
    @ApiResponse(responseCode = "200", description = "Operación realizada con éxito: Curso actualizado")
    @ApiResponse(responseCode = "400", description = "Petición errónea")
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
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
     * Elimina un curso dada su Id
     * @param id
     * @return
     */
    @Operation(summary = "Elimina un curso dada su Id")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Curso eliminado")
    @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
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
     * Elimina todos los cursos
     * @return
     */
    @Operation(summary = "Elimina todos los cursos")
    @ApiResponse(responseCode = "204", description = "Operación realizada con éxito: Cursos eliminados")
    @DeleteMapping
    public ResponseEntity<Curso> deleteAll(){
        log.info("Petición DELETE para eliminar todos los cursos");
        this.cursoService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
