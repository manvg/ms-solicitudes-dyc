package com.microservicio.ms_solicitudes_dyc.controller;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudDto;
import com.microservicio.ms_solicitudes_dyc.model.dto.ResponseModelDto;
import com.microservicio.ms_solicitudes_dyc.service.solicitud.SolicitudService;
import com.microservicio.ms_solicitudes_dyc.utilities.MensajesSolicitud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    //---------MÉTODOS GET---------//
    @GetMapping
    public ResponseEntity<List<SolicitudDto>> obtenerTodos() {
        return ResponseEntity.ok(solicitudService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.obtenerPorId(id));
    }

    //---------MÉTODOS POST---------//
    @PostMapping
    public ResponseEntity<ResponseModelDto> crear(@RequestBody SolicitudDto dto) {
        solicitudService.crear(dto);
        return ResponseEntity.ok(new ResponseModelDto(true, MensajesSolicitud.CREADO));
    }

    //---------MÉTODOS PUT---------//
    @PutMapping("/{id}")
    public ResponseEntity<ResponseModelDto> actualizar(@PathVariable Long id, @RequestBody SolicitudDto dto) {
        solicitudService.actualizar(id, dto);
        return ResponseEntity.ok(new ResponseModelDto(true, MensajesSolicitud.ACTUALIZADO));
    }

    //---------MÉTODOS DELETE---------//
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModelDto> eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
        return ResponseEntity.ok(new ResponseModelDto(true, MensajesSolicitud.ELIMINADO));
    }
}
