package com.microservicio.ms_solicitudes_dyc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoSolicitudDto {
    private Long idEstadoSolicitud;
    private String nombreEstado;
    private Integer activo;
}