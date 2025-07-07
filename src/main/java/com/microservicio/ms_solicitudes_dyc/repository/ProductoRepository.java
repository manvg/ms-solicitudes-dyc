package com.microservicio.ms_solicitudes_dyc.repository;

import com.microservicio.ms_solicitudes_dyc.model.entity.Producto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findById(Long id);
}
