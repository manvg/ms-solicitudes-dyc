package com.microservicio.ms_solicitudes_dyc.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudDto;
import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudImagenDto;
import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudProductoDto;
import com.microservicio.ms_solicitudes_dyc.model.entity.*;
import com.microservicio.ms_solicitudes_dyc.repository.*;
import com.microservicio.ms_solicitudes_dyc.service.solicitud.SolicitudServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SolicitudServiceImplTest {

    @Mock private SolicitudRepository solicitudRepository;
    @Mock private SolicitudBitacoraRepository solicitudBitacoraRepository;
    @Mock private SolicitudProductoRepository solicitudProductoRepository;
    @Mock private SolicitudImagenRepository solicitudImagenRepository;
    @Mock private ProductoRepository productoRepository;

    @InjectMocks
    private SolicitudServiceImpl service;

    private SolicitudDto dto;
    private Solicitud solicitud;

    @BeforeEach
    void setUp() {
        dto = new SolicitudDto();
        dto.setIdTipoSolicitud(2L);
        dto.setIdEstadoSolicitud(1L);
        dto.setNombreCliente("Juan");
        dto.setCorreoCliente("juan@email.com");
        dto.setUsuarioCreacion("admin");

        SolicitudProductoDto prodDto = new SolicitudProductoDto();
        prodDto.setIdProducto(10L);
        prodDto.setCantidad(2);
        dto.setProductos(List.of(prodDto));

        SolicitudImagenDto imgDto = new SolicitudImagenDto();
        imgDto.setNombre("img.jpg");
        imgDto.setExtension("jpg");
        imgDto.setDescripcion("Imagen test");
        imgDto.setUrlImagen("http://img");
        imgDto.setActivo(1);
        dto.setImagenes(List.of(imgDto));

        solicitud = new Solicitud();
        solicitud.setIdSolicitud(1L);
        solicitud.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void obtenerPorId_noExiste_lanzaExcepcion() {
        when(solicitudRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.obtenerPorId(1L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Solicitud no encontrada");
    }

    @Test
    void crear_guardarSolicitudYBitacora_ok() {
        // Simular save de Solicitud
        when(solicitudRepository.save(any(Solicitud.class))).thenAnswer(inv -> {
            Solicitud s = inv.getArgument(0);
            s.setIdSolicitud(1L);
            return s;
        });

        service.crear(dto);

        verify(solicitudRepository).save(any(Solicitud.class));

        verify(solicitudBitacoraRepository).save(any(SolicitudBitacora.class));

        verify(solicitudProductoRepository, times(1)).save(any(SolicitudProducto.class));
    }

    @Test
    void crear_tipoPersonalizada_guardaImagenes() {
        dto.setIdTipoSolicitud(3L);
        when(solicitudRepository.save(any(Solicitud.class))).thenAnswer(inv -> {
            Solicitud s = inv.getArgument(0);
            s.setIdSolicitud(2L);
            return s;
        });

        service.crear(dto);

        verify(solicitudImagenRepository, times(1)).save(any(SolicitudImagen.class));
    }

    @Test
    void actualizar_noExiste_lanzaExcepcion() {
        when(solicitudRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.actualizar(5L, dto))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Solicitud no encontrada");
    }

    @Test
    void actualizar_productoNoExiste_lanzaExcepcion() {
        Solicitud existente = new Solicitud();
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(existente));
        SolicitudProductoDto prodDto = new SolicitudProductoDto();
        prodDto.setIdProducto(99L);
        prodDto.setCantidad(1);
        dto.setProductos(List.of(prodDto));

        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.actualizar(1L, dto))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Producto no encontrado");
    }

    @Test
    void eliminar_noExiste_lanzaExcepcion() {
        when(solicitudRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> service.eliminar(1L))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Solicitud no encontrada");
    }

    @Test
    void eliminar_existente_eliminaOk() {
        when(solicitudRepository.existsById(1L)).thenReturn(true);
        service.eliminar(1L);
        verify(solicitudRepository).deleteById(1L);
    }
}

