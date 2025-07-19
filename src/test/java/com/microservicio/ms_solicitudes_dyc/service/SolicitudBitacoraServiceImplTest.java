package com.microservicio.ms_solicitudes_dyc.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudBitacoraDto;
import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudBitacora;
import com.microservicio.ms_solicitudes_dyc.model.entity.Solicitud;
import com.microservicio.ms_solicitudes_dyc.model.entity.EstadoSolicitud;
import com.microservicio.ms_solicitudes_dyc.repository.EstadoSolicitudRepository;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudBitacoraRepository;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudRepository;
import com.microservicio.ms_solicitudes_dyc.service.bitacora.SolicitudBitacoraServiceImpl;
import com.microservicio.ms_solicitudes_dyc.utilities.SolicitudBitacoraMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SolicitudBitacoraServiceImplTest {

    @Mock private SolicitudBitacoraRepository bitacoraRepository;
    @Mock private SolicitudRepository solicitudRepository;
    @Mock private EstadoSolicitudRepository estadoSolicitudRepository;
    @Mock private SolicitudBitacoraMapper mapper;

    @InjectMocks
    private SolicitudBitacoraServiceImpl service;

    private SolicitudBitacoraDto dto;
    private Solicitud solicitud;
    private EstadoSolicitud estadoSolicitud;

    @BeforeEach
    void setUp() {
        dto = new SolicitudBitacoraDto();
        dto.setIdSolicitud(1L);
        dto.setIdEstadoSolicitud(2L);
        dto.setDescripcion("Test desc");
        dto.setUsuarioCreacion("admin");

        solicitud = new Solicitud();
        solicitud.setIdSolicitud(1L);

        estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(2L);
    }

    @Test
    void obtenerPorSolicitud_retornaListaDtos() {
        SolicitudBitacora entity = new SolicitudBitacora();
        when(bitacoraRepository.findBySolicitudIdSolicitudOrderByFechaCreacionAsc(1L)).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        List<SolicitudBitacoraDto> result = service.obtenerPorSolicitud(1L);

        assertThat(result).hasSize(1);
        verify(bitacoraRepository).findBySolicitudIdSolicitudOrderByFechaCreacionAsc(1L);
        verify(mapper).toDto(entity);
    }

    @Test
    void crear_solicitudNoExiste_lanzaExcepcion() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.crear(dto))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Solicitud no encontrada");
    }

    @Test
    void crear_estadoNoExiste_lanzaExcepcion() {
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(estadoSolicitudRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.crear(dto))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Estado de solicitud no encontrado");
    }

    @Test
    void crear_estadoCambia_actualizaSolicitudYGuardaBitacora() {
        solicitud.setEstadoSolicitud(null);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(estadoSolicitudRepository.findById(2L)).thenReturn(Optional.of(estadoSolicitud));
        when(bitacoraRepository.save(any())).thenReturn(new SolicitudBitacora());

        service.crear(dto);

        verify(solicitudRepository).save(solicitud);
        verify(bitacoraRepository).save(any(SolicitudBitacora.class));
    }

    @Test
    void crear_estadoNoCambia_soloGuardaBitacora() {
        solicitud.setEstadoSolicitud(estadoSolicitud);
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(solicitud));
        when(estadoSolicitudRepository.findById(2L)).thenReturn(Optional.of(estadoSolicitud));

        service.crear(dto);

        verify(solicitudRepository, never()).save(solicitud);
        verify(bitacoraRepository).save(any(SolicitudBitacora.class));
    }

    @Test
    void actualizar_bitacoraNoExiste_lanzaExcepcion() {
        when(bitacoraRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.actualizar(1L, dto))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Bitácora no encontrada");
    }

    @Test
    void actualizar_estadoNoExiste_lanzaExcepcion() {
        SolicitudBitacora bitacora = new SolicitudBitacora();
        when(bitacoraRepository.findById(1L)).thenReturn(Optional.of(bitacora));
        dto.setIdEstadoSolicitud(5L);
        when(estadoSolicitudRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.actualizar(1L, dto))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Estado de solicitud no encontrado");
    }

    @Test
    void actualizar_estadoValido_actualizaYGuarda() {
        SolicitudBitacora bitacora = new SolicitudBitacora();
        when(bitacoraRepository.findById(1L)).thenReturn(Optional.of(bitacora));
        dto.setIdEstadoSolicitud(2L);
        when(estadoSolicitudRepository.findById(2L)).thenReturn(Optional.of(estadoSolicitud));

        service.actualizar(1L, dto);

        verify(bitacoraRepository).save(bitacora);
        assertThat(bitacora.getEstadoSolicitud()).isEqualTo(estadoSolicitud);
        assertThat(bitacora.getDescripcion()).isEqualTo("Test desc");
    }

    @Test
    void eliminar_bitacoraNoExiste_lanzaExcepcion() {
        when(bitacoraRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> service.eliminar(1L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Bitácora no encontrada");
    }

    @Test
    void eliminar_existente_eliminaOk() {
        when(bitacoraRepository.existsById(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(bitacoraRepository).deleteById(1L);
    }
}