package com.microservicio.ms_solicitudes_dyc.utilities;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudBitacoraDto;
import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudBitacora;
import org.springframework.stereotype.Component;

@Component
public class SolicitudBitacoraMapper {

    public SolicitudBitacoraDto toDto(SolicitudBitacora entity) {
        SolicitudBitacoraDto dto = new SolicitudBitacoraDto();
        dto.setIdSolicitudBitacora(entity.getIdSolicitudBitacora());
        dto.setIdSolicitud(entity.getSolicitud().getIdSolicitud());
        dto.setIdEstadoSolicitud(entity.getEstadoSolicitud().getIdEstadoSolicitud());
        dto.setNombreEstado(entity.getEstadoSolicitud().getNombreEstado());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setUsuarioCreacion(entity.getUsuarioCreacion());
        return dto;
    }
}
