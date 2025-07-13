package com.microservicio.ms_solicitudes_dyc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudProductoDto {
    private Long idSolicitud;
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
}
