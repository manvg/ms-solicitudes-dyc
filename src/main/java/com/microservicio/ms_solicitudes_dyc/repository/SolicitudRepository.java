package com.microservicio.ms_solicitudes_dyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservicio.ms_solicitudes_dyc.model.entity.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

}
