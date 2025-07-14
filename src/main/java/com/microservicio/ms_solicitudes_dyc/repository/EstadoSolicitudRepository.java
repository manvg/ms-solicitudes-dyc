package com.microservicio.ms_solicitudes_dyc.repository;

import com.microservicio.ms_solicitudes_dyc.model.entity.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitud, Long> {

}