package com.microservicio.ms_solicitudes_dyc.model.entity;

import java.util.Objects;

public class SolicitudProductoId {

    private Long solicitud;
    private Long producto;

    public SolicitudProductoId() {}

    public SolicitudProductoId(Long solicitud, Long producto) {
        this.solicitud = solicitud;
        this.producto = producto;
    }

    public Long getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Long solicitud) {
        this.solicitud = solicitud;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudProductoId)) return false;
        SolicitudProductoId that = (SolicitudProductoId) o;
        return Objects.equals(solicitud, that.solicitud) &&
               Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solicitud, producto);
    }
}