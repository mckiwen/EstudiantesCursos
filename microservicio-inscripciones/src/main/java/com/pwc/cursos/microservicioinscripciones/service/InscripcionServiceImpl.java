package com.pwc.cursos.microservicioinscripciones.service;

import com.pwc.cursos.microservicioinscripciones.dtos.CursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.EstudianteDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionCursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionesDTO;
import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.entity.InscripcionId;
import com.pwc.cursos.microservicioinscripciones.repository.InscripcionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    private final Logger log = LoggerFactory.getLogger(InscripcionServiceImpl.class);

    private InscripcionRepository inscripcionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public InscripcionServiceImpl(InscripcionRepository inscripcionRepository){
        this.inscripcionRepository = inscripcionRepository;
    }

    @Override
    public List<Inscripcion> findAllByIdCurso(Long idCurso) {
        return this.inscripcionRepository.findAllByIdCurso(idCurso);
    }

    @Override
    public Optional<InscripcionCursoDTO> findByIdCurso(Long idCurso) {
        boolean exists = this.inscripcionRepository.existsByIdCurso(idCurso);
        if(!exists){
            return Optional.empty();
        }
        List<Inscripcion> listInscripcion = findAllByIdCurso(idCurso);
        ResponseEntity<CursoDTO> responseCurso = restTemplate.getForEntity(
                "http://localhost:8080/api/cursos/" + idCurso,
                CursoDTO.class);

        List<EstudianteDTO> listEstudiantes = new ArrayList<>();
        for(Inscripcion inscripcion : listInscripcion){
            ResponseEntity<EstudianteDTO> responseEstudiante = restTemplate.getForEntity(
                    "http://localhost:8081/api/estudiantes/" + inscripcion.getIdEstudiante(),
                    EstudianteDTO.class);
            listEstudiantes.add(responseEstudiante.getBody());
        }

        CursoDTO curso = responseCurso.getBody();
        InscripcionCursoDTO inscripcionCursoDTO = new InscripcionCursoDTO(idCurso,
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getFechaInicio(),
                curso.getFechaFin(),
                listEstudiantes);

        return Optional.of(inscripcionCursoDTO);
    }

    @Override
    public InscripcionesDTO save(InscripcionesDTO inscripcionesDTO) {
        Long idCurso = inscripcionesDTO.getIdCurso();
        List<Long> listIdEstudiante = inscripcionesDTO.getIdEstudianteList();
        List<Long> listEstudianteInscrito = new ArrayList<>();
        for(Long idEstudiante : listIdEstudiante){
            Inscripcion inscripcion = new Inscripcion(idCurso, idEstudiante);
            if(!existsByInscripcion(inscripcion)){
                log.info("Inscripcion realizada con éxito");
                this.inscripcionRepository.save(inscripcion);
                listEstudianteInscrito.add(idEstudiante);
            } else {
                log.warn("Inscripcion idCurso: " + idCurso + " idEstudiante: " + idEstudiante + " ya existe.");
            }
        }
        inscripcionesDTO.setIdEstudianteList(listEstudianteInscrito);
        return inscripcionesDTO;
    }

    @Override
    public InscripcionesDTO delete(InscripcionesDTO inscripcionesDTO) {
        Long idCurso = inscripcionesDTO.getIdCurso();
        List<Long> listIdEstudiante = inscripcionesDTO.getIdEstudianteList();
        List<Long> listEstudianteBaja = new ArrayList<>();
        for(Long idEstudiante : listIdEstudiante){
            Inscripcion inscripcion = new Inscripcion(idCurso, idEstudiante);
            if(existsByInscripcion(inscripcion)){
                log.info("Baja realizada con éxito");
                this.inscripcionRepository.delete(inscripcion);
                listEstudianteBaja.add(idEstudiante);
            } else {
                log.warn("Inscripcion idCurso: " + idCurso + " idEstudiante: " + idEstudiante + " no existe.");
            }
        }
        inscripcionesDTO.setIdEstudianteList(listEstudianteBaja);
        return inscripcionesDTO;
    }

    @Override
    public void deleteByIdEstudiante(Long idEstudiante) {
        this.inscripcionRepository.deleteAllByIdEstudiante(idEstudiante);
    }


    @Override
    public boolean existsByInscripcion(Inscripcion inscripcion) {
        Long idCurso = inscripcion.getIdCurso();
        Long idEstudiante = inscripcion.getIdEstudiante();
        return this.inscripcionRepository.existsByIdCursoAndIdEstudiante(idCurso, idEstudiante);
    }


}
