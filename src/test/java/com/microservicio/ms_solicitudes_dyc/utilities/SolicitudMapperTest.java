package com.microservicio.ms_solicitudes_dyc.utilities;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudDto;
import com.microservicio.ms_solicitudes_dyc.model.entity.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SolicitudMapperTest {

    @Test
    void toDto_entidadNula_retornaNull() {
        assertThat(SolicitudMapper.toDto(null)).isNull();
    }

    @Test
    void toEntity_dtoNulo_retornaNull() {
        assertThat(SolicitudMapper.toEntity(null)).isNull();
    }

    @Test
    void toDto_mapeaCamposSimplesYListas() {
        Solicitud entidad = new Solicitud();
        entidad.setIdSolicitud(1L);
        entidad.setNombreCliente("Juan");
        entidad.setCorreoCliente("mail@mail.com");
        entidad.setTelefonoCliente("123456");
        entidad.setObservaciones("Obs");
        entidad.setFechaCreacion(LocalDateTime.of(2023, 1, 1, 12, 0));

        TipoSolicitud tipo = new TipoSolicitud();
        tipo.setIdTipoSolicitud(2L);
        tipo.setNombreSolicitud("TipoX");
        entidad.setTipoSolicitud(tipo);

        EstadoSolicitud estado = new EstadoSolicitud();
        estado.setIdEstadoSolicitud(5L);
        estado.setNombreEstado("En Proceso");
        entidad.setEstadoSolicitud(estado);

        Servicio servicio = new Servicio();
        servicio.setIdServicio(3L);
        servicio.setNombre("Grabado");
        entidad.setServicio(servicio);

        Producto producto = new Producto();
        producto.setIdProducto(11L);
        producto.setNombre("Lapiz");

        SolicitudProducto solProd = new SolicitudProducto(entidad, producto, 4);
        entidad.setProductos(List.of(solProd));

        SolicitudImagen solImg = new SolicitudImagen();
        solImg.setIdSolicitudImagen(21L);
        solImg.setSolicitud(entidad);
        solImg.setNombre("img.jpg");
        solImg.setExtension("jpg");
        solImg.setDescripcion("desc");
        solImg.setUrlImagen("http://img");
        solImg.setActivo(1);
        entidad.setImagenes(List.of(solImg));

        SolicitudDto dto = SolicitudMapper.toDto(entidad);

        assertThat(dto).isNotNull();
        assertThat(dto.getIdSolicitud()).isEqualTo(1L);
        assertThat(dto.getNombreCliente()).isEqualTo("Juan");
        assertThat(dto.getCorreoCliente()).isEqualTo("mail@mail.com");
        assertThat(dto.getIdTipoSolicitud()).isEqualTo(2L);
        assertThat(dto.getNombreTipoSolicitud()).isEqualTo("TipoX");
        assertThat(dto.getIdEstadoSolicitud()).isEqualTo(5L);
        assertThat(dto.getNombreEstadoSolicitud()).isEqualTo("En Proceso");
        assertThat(dto.getIdServicio()).isEqualTo(3L);
        assertThat(dto.getNombreServicio()).isEqualTo("Grabado");
        assertThat(dto.getProductos()).hasSize(1);
        assertThat(dto.getProductos().get(0).getIdProducto()).isEqualTo(11L);
        assertThat(dto.getProductos().get(0).getCantidad()).isEqualTo(4);
        assertThat(dto.getImagenes()).hasSize(1);
        assertThat(dto.getImagenes().get(0).getIdSolicitudImagen()).isEqualTo(21L);
        assertThat(dto.getImagenes().get(0).getNombre()).isEqualTo("img.jpg");
    }

    @Test
    void toEntity_mapeaCamposSimples() {
        SolicitudDto dto = new SolicitudDto();
        dto.setIdSolicitud(9L);
        dto.setNombreCliente("Ana");
        dto.setCorreoCliente("a@mail.com");
        dto.setTelefonoCliente("999");
        dto.setObservaciones("Obs2");
        dto.setFechaCreacion(LocalDateTime.of(2024, 2, 2, 10, 30));
        dto.setIdTipoSolicitud(7L);
        dto.setIdEstadoSolicitud(8L);
        dto.setIdServicio(6L);

        Solicitud entidad = SolicitudMapper.toEntity(dto);

        assertThat(entidad).isNotNull();
        assertThat(entidad.getIdSolicitud()).isEqualTo(9L);
        assertThat(entidad.getNombreCliente()).isEqualTo("Ana");
        assertThat(entidad.getCorreoCliente()).isEqualTo("a@mail.com");
        assertThat(entidad.getTipoSolicitud()).isNotNull();
        assertThat(entidad.getTipoSolicitud().getIdTipoSolicitud()).isEqualTo(7L);
        assertThat(entidad.getEstadoSolicitud()).isNotNull();
        assertThat(entidad.getEstadoSolicitud().getIdEstadoSolicitud()).isEqualTo(8L);
        assertThat(entidad.getServicio()).isNotNull();
        assertThat(entidad.getServicio().getIdServicio()).isEqualTo(6L);
    }

    @Test
    void tipoSolicitudFromId_idNull_retornaNull() {
        assertThat(SolicitudMapper.tipoSolicitudFromId(null)).isNull();
    }

    @Test
    void tipoSolicitudFromId_idValido_retornaTipoSolicitud() {
        assertThat(SolicitudMapper.tipoSolicitudFromId(11L)).isNotNull();
        assertThat(SolicitudMapper.tipoSolicitudFromId(11L).getIdTipoSolicitud()).isEqualTo(11L);
    }

    @Test
    void estadoSolicitudFromId_idNull_retornaNull() {
        assertThat(SolicitudMapper.estadoSolicitudFromId(null)).isNull();
    }

    @Test
    void estadoSolicitudFromId_idValido_retornaEstadoSolicitud() {
        assertThat(SolicitudMapper.estadoSolicitudFromId(12L)).isNotNull();
        assertThat(SolicitudMapper.estadoSolicitudFromId(12L).getIdEstadoSolicitud()).isEqualTo(12L);
    }
}
