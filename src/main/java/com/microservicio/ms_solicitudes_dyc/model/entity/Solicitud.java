package com.microservicio.ms_solicitudes_dyc.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SOLICITUD")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOLICITUD")
    private Long idSolicitud;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_SOLICITUD", nullable = false)
    private TipoSolicitud tipoSolicitud;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO_SOLICITUD", nullable = false)
    private EstadoSolicitud estadoSolicitud;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "NOMBRE_CLIENTE", nullable = false, length = 100)
    private String nombreCliente;

    @Column(name = "CORREO_CLIENTE", nullable = false, length = 100)
    private String correoCliente;

    @Column(name = "TELEFONO_CLIENTE", length = 20)
    private String telefonoCliente;

    @Column(name = "OBSERVACIONES", length = 500)
    private String observaciones;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudProducto> productos;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudImagen> imagenes;

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<SolicitudProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<SolicitudProducto> productos) {
        this.productos = productos;
    }

    public List<SolicitudImagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<SolicitudImagen> imagenes) {
        this.imagenes = imagenes;
    }
}
