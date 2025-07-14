package com.microservicio.ms_solicitudes_dyc.service.solicitud;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudDto;

import java.util.List;

public interface SolicitudService {

    List<SolicitudDto> obtenerTodos();
    SolicitudDto obtenerPorId(Long id);
    void crear(SolicitudDto dto);
    void actualizar(Long id, SolicitudDto dto);
    void eliminar(Long id);
}
