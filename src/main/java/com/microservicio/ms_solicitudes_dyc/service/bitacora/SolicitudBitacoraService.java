package com.microservicio.ms_solicitudes_dyc.service.bitacora;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudBitacoraDto;
import java.util.List;

public interface SolicitudBitacoraService {
    List<SolicitudBitacoraDto> obtenerPorSolicitud(Long idSolicitud);
    void crear(SolicitudBitacoraDto dto);
    void actualizar(Long idBitacora, SolicitudBitacoraDto dto);
    void eliminar(Long idBitacora);
}