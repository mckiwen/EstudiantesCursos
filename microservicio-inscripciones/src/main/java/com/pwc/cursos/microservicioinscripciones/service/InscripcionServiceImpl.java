package com.pwc.cursos.microservicioinscripciones.service;

import com.pwc.cursos.microservicioinscripciones.dtos.CursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.EstudianteDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionCursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionesDTO;
import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.repository.InscripcionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    @Value("${cursos-url}")
    private String hostCursos;

    @Value("${estudiantes-url}")
    private String hostEstudiantes;

    private final Logger log = LoggerFactory.getLogger(InscripcionServiceImpl.class);

    private InscripcionRepository inscripcionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public InscripcionServiceImpl(InscripcionRepository inscripcionRepository){
        this.inscripcionRepository = inscripcionRepository;
    }

    /**
     * Metodo que busca todas las inscripciones por idCurso.
     * @param idCurso Id del Curso
     * @return List
     */
    @Override
    public List<Inscripcion> findAllByIdCurso(Long idCurso) {
        return this.inscripcionRepository.findAllByIdCurso(idCurso);
    }

    /**
     * Metodo que busca las inscripciones que tiene un curso dado su idCurso.
     * Devuelva la informacion completa del curso y de los estudiantes mediante peticiones HTTP.
     * @param idCurso Id del curso
     * @return InscripcionCursoDTO
     */
    @Override
    public Optional<InscripcionCursoDTO> findByIdCurso(Long idCurso) {
        boolean exists = this.inscripcionRepository.existsByIdCurso(idCurso);
        if (!exists) {
            return Optional.empty();
        }
        ResponseEntity<CursoDTO> responseCurso;
        try {
            responseCurso = restTemplate.getForEntity(
                    "http://" + hostCursos +":8080/api/cursos/" + idCurso,
                    CursoDTO.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }

        List<Inscripcion> listInscripcion = findAllByIdCurso(idCurso);
        List<EstudianteDTO> listEstudiantes = new ArrayList<>();
        for (Inscripcion inscripcion : listInscripcion) {
            try{
                ResponseEntity<EstudianteDTO> responseEstudiante = restTemplate.getForEntity(
                        "http://" + hostEstudiantes +":8081/api/estudiantes/" + inscripcion.getIdEstudiante(),
                        EstudianteDTO.class);
                listEstudiantes.add(responseEstudiante.getBody());
            } catch (Exception e){
                log.warn(e.getMessage());
            }
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

    /**
     * Metodo que guarda en el repositorio nuevas inscripciones dados por idCurso e ids de estudiantes.
     * Comprueba si la inscripcion ya existe, en caso contrario, la guarda. Evita duplicados.
     * @param inscripcionesDTO Objeto inscripcionesDTO
     * @return InscripcionesDTO
     */
    @Override
    @Transactional
    public InscripcionesDTO save(InscripcionesDTO inscripcionesDTO) {
        Long idCurso = inscripcionesDTO.getIdCurso();
        List<Long> listIdEstudiante = inscripcionesDTO.getIdEstudianteList();
        List<Long> listEstudianteInscrito = new ArrayList<>();
        for(Long idEstudiante : listIdEstudiante){
            Inscripcion inscripcion = new Inscripcion(idCurso, idEstudiante);
            if(!existsByInscripcion(inscripcion)){
                this.inscripcionRepository.save(inscripcion);
                log.info("Inscripcion realizada con éxito");
                listEstudianteInscrito.add(idEstudiante);
            } else {
                log.error("Inscripcion idCurso: " + idCurso + " idEstudiante: " + idEstudiante + " ya existe.");
            }
        }
        inscripcionesDTO.setIdEstudianteList(listEstudianteInscrito);
        return inscripcionesDTO;
    }

    /**
     * Metodo que elimina inscripciones del repositorio dado idCurso e ids de estudiantes.
     * Comprueba si existe el registro, en caso afirmativo lo elimina.
     * @param inscripcionesDTO InscripcionesDTO
     * @return inscripcionesDTO
     */
    @Override
    @Transactional
    public InscripcionesDTO delete(InscripcionesDTO inscripcionesDTO) {
        Long idCurso = inscripcionesDTO.getIdCurso();
        List<Long> listIdEstudiante = inscripcionesDTO.getIdEstudianteList();
        List<Long> listEstudianteBaja = new ArrayList<>();
        for(Long idEstudiante : listIdEstudiante){
            Inscripcion inscripcion = new Inscripcion(idCurso, idEstudiante);
            if(existsByInscripcion(inscripcion)){
                this.inscripcionRepository.delete(inscripcion);
                log.info("Baja realizada con éxito");
                listEstudianteBaja.add(idEstudiante);
            } else {
                log.error("Inscripcion idCurso: " + idCurso + " idEstudiante: " + idEstudiante + " no existe.");
            }
        }
        inscripcionesDTO.setIdEstudianteList(listEstudianteBaja);
        return inscripcionesDTO;
    }

    /**
     * Metodo que elimina todas las inscripciones de un estudiante dado su idEstudiante.
     * @param idEstudiante Id del estudiante
     */
    @Override
    @Transactional
    public void deleteByIdEstudiante(Long idEstudiante) {
        this.inscripcionRepository.deleteAllByIdEstudiante(idEstudiante);
    }

    /**
     * Metodo que elimina todas las inscripciones de un curso dado su idCurso.
     * @param idCurso Id del curso
     */
    @Override
    @Transactional
    public void deleteByIdCurso(Long idCurso) {
        this.inscripcionRepository.deleteAllByIdCurso(idCurso);
    }

    /**
     * Metodo que elimina todas las inscripciones.
     */
    @Override
    @Transactional
    public void deleteAll() {
        this.inscripcionRepository.deleteAll();
    }


    /**
     * Metodo que comprueba si una Inscripcion existe.
     * @param inscripcion Inscripcion
     * @return boolean
     */
    @Override
    public boolean existsByInscripcion(Inscripcion inscripcion) {
        Long idCurso = inscripcion.getIdCurso();
        Long idEstudiante = inscripcion.getIdEstudiante();
        return this.inscripcionRepository.existsByIdCursoAndIdEstudiante(idCurso, idEstudiante);
    }
}
