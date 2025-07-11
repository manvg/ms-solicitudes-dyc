package com.microservicio.ms_solicitudes_dyc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDto {
    private Long idSolicitud;
    private Long idTipoSolicitud;
    private Long idEstadoSolicitud;
    private LocalDateTime fechaCreacion;
    private String nombreCliente;
    private String correoCliente;
    private String telefonoCliente;
    private String observaciones;
    private List<SolicitudProductoDto> productos;
    private List<SolicitudImagenDto> imagenes;
}
