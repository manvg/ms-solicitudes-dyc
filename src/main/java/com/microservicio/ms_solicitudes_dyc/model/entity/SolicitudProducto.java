package com.microservicio.ms_solicitudes_dyc.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SOLICITUD_PRODUCTO")
@IdClass(SolicitudProductoId.class)
public class SolicitudProducto {

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_SOLICITUD", nullable = false)
    private Solicitud solicitud;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
    private Producto producto;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public SolicitudProducto() {}

    public SolicitudProducto(Solicitud solicitud, Producto producto, Integer cantidad) {
        this.solicitud = solicitud;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public Producto getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
