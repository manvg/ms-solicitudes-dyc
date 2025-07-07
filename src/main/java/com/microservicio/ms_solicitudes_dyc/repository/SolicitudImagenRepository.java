package com.microservicio.ms_solicitudes_dyc.repository;

import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudImagenRepository extends JpaRepository<SolicitudImagen, Long> {
    List<SolicitudImagen> findBySolicitud_IdSolicitud(Long idSolicitud);
    List<SolicitudImagen> findBySolicitud_IdSolicitudAndActivo(Long idSolicitud, Integer activo);
}
