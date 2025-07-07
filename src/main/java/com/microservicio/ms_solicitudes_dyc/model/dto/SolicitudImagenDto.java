package com.microservicio.ms_solicitudes_dyc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudImagenDto {
    private Long idSolicitudImagen;
    private Long idSolicitud;
    private String nombre;
    private String extension;
    private String descripcion;
    private String urlImagen;
    private Integer activo;
}