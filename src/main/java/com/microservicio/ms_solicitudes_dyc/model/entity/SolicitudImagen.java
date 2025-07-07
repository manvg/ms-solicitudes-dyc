package com.microservicio.ms_solicitudes_dyc.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SOLICITUD_IMAGEN")
public class SolicitudImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOLICITUD_IMAGEN")
    private Long idSolicitudImagen;

    @ManyToOne
    @JoinColumn(name = "ID_SOLICITUD", nullable = false)
    private Solicitud solicitud;

    @Column(name = "NOMBRE", nullable = false, length = 500)
    private String nombre;

    @Column(name = "EXTENSION", nullable = false, length = 5)
    private String extension;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "URL_IMAGEN", length = 500)
    private String urlImagen;

    @Column(name = "ACTIVO", nullable = false)
    private Integer activo = 1;

    public SolicitudImagen() {}

    public Long getIdSolicitudImagen() {
        return idSolicitudImagen;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getExtension() {
        return extension;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Integer getActivo() {
        return activo;
    }

    // Setters
    public void setIdSolicitudImagen(Long idSolicitudImagen) {
        this.idSolicitudImagen = idSolicitudImagen;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}
