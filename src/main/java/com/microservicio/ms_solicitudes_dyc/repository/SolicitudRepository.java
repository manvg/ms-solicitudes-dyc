package com.microservicio.ms_solicitudes_dyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.microservicio.ms_solicitudes_dyc.model.entity.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstadoSolicitud_Activo(Integer activo);
    
}
