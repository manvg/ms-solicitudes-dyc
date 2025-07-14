package com.microservicio.ms_solicitudes_dyc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudBitacoraDto {
    private Long idSolicitudBitacora;
    private Long idSolicitud;
    private Long idEstadoSolicitud;
    private String nombreEstado;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private String usuarioCreacion;
}