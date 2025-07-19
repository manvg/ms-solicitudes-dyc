package com.microservicio.ms_solicitudes_dyc.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudProducto;
import com.microservicio.ms_solicitudes_dyc.model.entity.Solicitud;
import com.microservicio.ms_solicitudes_dyc.model.entity.Producto;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudProductoRepository;
import com.microservicio.ms_solicitudes_dyc.service.solicitudProducto.SolicitudProductoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SolicitudProductoServiceImplTest {

    @Mock
    private SolicitudProductoRepository solicitudProductoRepository;

    @InjectMocks
    private SolicitudProductoServiceImpl service;

    private SolicitudProducto producto;
    private Solicitud solicitud;
    private Producto prod;

    @BeforeEach
    void setUp() {
        solicitud = new Solicitud();

        prod = new Producto();

        producto = new SolicitudProducto(solicitud, prod, 5);
    }

    @Test
    void listarPorIdSolicitud_retornaLista() {
        when(solicitudProductoRepository.findBySolicitud_IdSolicitud(1L)).thenReturn(List.of(producto));

        List<SolicitudProducto> result = service.listarPorIdSolicitud(1L);

        assertThat(result).hasSize(1);
        verify(solicitudProductoRepository).findBySolicitud_IdSolicitud(1L);
    }

    @Test
    void eliminarPorIdSolicitud_ok() {
        doNothing().when(solicitudProductoRepository).deleteBySolicitud_IdSolicitud(1L);

        service.eliminarPorIdSolicitud(1L);

        verify(solicitudProductoRepository).deleteBySolicitud_IdSolicitud(1L);
    }

    @Test
    void guardar_ok() {
        when(solicitudProductoRepository.save(producto)).thenReturn(producto);

        service.guardar(producto);

        verify(solicitudProductoRepository).save(producto);
    }

    @Test
    void guardarTodos_ok() {
        List<SolicitudProducto> productos = List.of(producto);

        when(solicitudProductoRepository.saveAll(productos)).thenReturn(productos);

        service.guardarTodos(productos);

        verify(solicitudProductoRepository).saveAll(productos);
    }
}