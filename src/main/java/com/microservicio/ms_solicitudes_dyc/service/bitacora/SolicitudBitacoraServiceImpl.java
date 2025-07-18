package com.microservicio.ms_solicitudes_dyc.service.bitacora;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudBitacoraDto;
import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudBitacora;
import com.microservicio.ms_solicitudes_dyc.model.entity.Solicitud;
import com.microservicio.ms_solicitudes_dyc.model.entity.EstadoSolicitud;
import com.microservicio.ms_solicitudes_dyc.repository.EstadoSolicitudRepository;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudBitacoraRepository;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudRepository;
import com.microservicio.ms_solicitudes_dyc.utilities.SolicitudBitacoraMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudBitacoraServiceImpl implements SolicitudBitacoraService {

    @Autowired
    private SolicitudBitacoraRepository bitacoraRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private EstadoSolicitudRepository estadoSolicitudRepository;

    @Autowired
    private SolicitudBitacoraMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudBitacoraDto> obtenerPorSolicitud(Long idSolicitud) {
        List<SolicitudBitacora> bitacoras = bitacoraRepository.findBySolicitudIdSolicitudOrderByFechaCreacionAsc(idSolicitud);
        return bitacoras.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void crear(SolicitudBitacoraDto dto) {
        Solicitud solicitud = solicitudRepository.findById(dto.getIdSolicitud())
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        EstadoSolicitud nuevoEstado = estadoSolicitudRepository.findById(dto.getIdEstadoSolicitud())
            .orElseThrow(() -> new RuntimeException("Estado de solicitud no encontrado"));
    
        if (solicitud.getEstadoSolicitud() == null || 
            !solicitud.getEstadoSolicitud().getIdEstadoSolicitud().equals(nuevoEstado.getIdEstadoSolicitud())) {
            solicitud.setEstadoSolicitud(nuevoEstado);
            solicitudRepository.save(solicitud);
        }
    
        SolicitudBitacora bitacora = new SolicitudBitacora();
        bitacora.setSolicitud(solicitud);
        bitacora.setEstadoSolicitud(nuevoEstado);
        bitacora.setDescripcion(dto.getDescripcion());
        bitacora.setFechaCreacion(LocalDateTime.now());
        bitacora.setUsuarioCreacion(dto.getUsuarioCreacion());
    
        bitacoraRepository.save(bitacora);
    }
    

    @Override
    @Transactional
    public void actualizar(Long idBitacora, SolicitudBitacoraDto dto) {
        SolicitudBitacora bitacora = bitacoraRepository.findById(idBitacora)
            .orElseThrow(() -> new RuntimeException("Bitácora no encontrada"));
        if (dto.getIdEstadoSolicitud() != null) {
            EstadoSolicitud estado = estadoSolicitudRepository.findById(dto.getIdEstadoSolicitud())
                .orElseThrow(() -> new RuntimeException("Estado de solicitud no encontrado"));
            bitacora.setEstadoSolicitud(estado);
        }
        bitacora.setDescripcion(dto.getDescripcion());
        bitacoraRepository.save(bitacora);
    }

    @Override
    @Transactional
    public void eliminar(Long idBitacora) {
        if (!bitacoraRepository.existsById(idBitacora)) {
            throw new RuntimeException("Bitácora no encontrada");
        }
        bitacoraRepository.deleteById(idBitacora);
    }
}