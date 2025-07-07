package com.microservicio.ms_solicitudes_dyc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudProducto;
import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudProductoId;

public interface SolicitudProductoRepository extends JpaRepository<SolicitudProducto, SolicitudProductoId> {
    List<SolicitudProducto> findBySolicitud_IdSolicitud(Long idSolicitud);
    List<SolicitudProducto> findByProducto_IdProducto(Long idProducto);
    void deleteBySolicitud_IdSolicitud(Long idSolicitud);
}
