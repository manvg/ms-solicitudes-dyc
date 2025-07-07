package com.microservicio.ms_solicitudes_dyc.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ESTADO_SOLICITUD")
public class EstadoSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO_SOLICITUD")
    private Long idEstadoSolicitud;

    @Column(name = "NOMBRE_ESTADO", nullable = false, length = 30)
    private String nombreEstado;

    @Column(name = "ACTIVO", nullable = false)
    private Integer activo;

    public Long getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setIdEstadoSolicitud(Long idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}