package com.microservicio.ms_solicitudes_dyc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoSolicitudDto {
    private Long idTipoSolicitud;
    private String nombreSolicitud;
    private Integer activo;
}
