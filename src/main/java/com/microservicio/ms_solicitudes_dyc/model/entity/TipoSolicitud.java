package com.microservicio.ms_solicitudes_dyc.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_SOLICITUD")
public class TipoSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_SOLICITUD")
    private Long idTipoSolicitud;

    @Column(name = "NOMBRE_SOLICITUD", nullable = false, length = 20)
    private String nombreSolicitud;

    @Column(name = "ACTIVO", nullable = false)
    private Integer activo;

    public Long getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public String getNombreSolicitud() {
        return nombreSolicitud;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setIdTipoSolicitud(Long idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public void setNombreSolicitud(String nombreSolicitud) {
        this.nombreSolicitud = nombreSolicitud;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}