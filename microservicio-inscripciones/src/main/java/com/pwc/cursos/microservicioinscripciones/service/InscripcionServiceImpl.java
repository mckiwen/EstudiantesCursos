package com.pwc.cursos.microservicioinscripciones.service;

import com.pwc.cursos.microservicioinscripciones.dtos.CursoDTO;
import com.pwc.cursos.microservicioinscripciones.dtos.InscripcionCursoDTO;
import com.pwc.cursos.microservicioinscripciones.entity.Inscripcion;
import com.pwc.cursos.microservicioinscripciones.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServiceImpl implements InscripcionService {

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
        List<Inscripcion> listInscripcion = findAllByIdCurso(idCurso);
        ResponseEntity<CursoDTO> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/cursos/" + idCurso, CursoDTO.class);

        System.out.println(responseEntity.getBody());
        CursoDTO response = responseEntity.getBody();
        InscripcionCursoDTO inscripcionCursoDTO = new InscripcionCursoDTO();
        inscripcionCursoDTO.setIdCurso(idCurso);
        inscripcionCursoDTO.setNombre(response.getNombre());
        inscripcionCursoDTO.setDescripcion(response.getDescripcion());
        inscripcionCursoDTO.setFechaInicio(response.getFechaInicio());
        inscripcionCursoDTO.setFechaFin(response.getFechaFin());
        inscripcionCursoDTO.setListEstudiante(new ArrayList<>());
        return Optional.of(inscripcionCursoDTO);
    }
}
