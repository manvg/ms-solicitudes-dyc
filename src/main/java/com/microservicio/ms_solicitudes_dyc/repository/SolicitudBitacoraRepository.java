package com.microservicio.ms_solicitudes_dyc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudBitacora;

public interface SolicitudBitacoraRepository extends JpaRepository<SolicitudBitacora, Long> {
    List<SolicitudBitacora> findByEstadoSolicitud_Activo(Integer activo);
}