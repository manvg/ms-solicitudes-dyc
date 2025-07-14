package com.microservicio.ms_solicitudes_dyc.controller;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudBitacoraDto;
import com.microservicio.ms_solicitudes_dyc.service.bitacora.SolicitudBitacoraService;
import com.microservicio.ms_solicitudes_dyc.model.dto.ResponseModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes/bitacora")
public class BitacoraController {

    @Autowired
    private SolicitudBitacoraService bitacoraService;

    @GetMapping("/{idSolicitud}")
    public ResponseEntity<List<SolicitudBitacoraDto>> obtenerPorSolicitud(@PathVariable Long idSolicitud) {
        return ResponseEntity.ok(bitacoraService.obtenerPorSolicitud(idSolicitud));
    }

    @PostMapping
    public ResponseEntity<ResponseModelDto> crear(@RequestBody SolicitudBitacoraDto dto) {
        bitacoraService.crear(dto);
        return ResponseEntity.ok(new ResponseModelDto(true, "Bitácora creada correctamente"));
    }

    @PutMapping("/{idBitacora}")
    public ResponseEntity<ResponseModelDto> actualizar(@PathVariable Long idBitacora, @RequestBody SolicitudBitacoraDto dto) {
        bitacoraService.actualizar(idBitacora, dto);
        return ResponseEntity.ok(new ResponseModelDto(true, "Bitácora actualizada correctamente"));
    }

    @DeleteMapping("/{idBitacora}")
    public ResponseEntity<ResponseModelDto> eliminar(@PathVariable Long idBitacora) {
        bitacoraService.eliminar(idBitacora);
        return ResponseEntity.ok(new ResponseModelDto(true, "Bitácora eliminada correctamente"));
    }
}
