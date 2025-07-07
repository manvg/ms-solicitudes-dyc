package com.microservicio.ms_solicitudes_dyc.utilities;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudDto;
import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudProductoDto;
import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudImagenDto;
import com.microservicio.ms_solicitudes_dyc.model.entity.Solicitud;
import com.microservicio.ms_solicitudes_dyc.model.entity.TipoSolicitud;
import com.microservicio.ms_solicitudes_dyc.model.entity.EstadoSolicitud;

import java.util.List;
import java.util.stream.Collectors;

public class SolicitudMapper {

    // ---- ENTITY -> DTO ----
    public static SolicitudDto toDto(Solicitud entidad) {
        if (entidad == null) return null;

        List<SolicitudProductoDto> productos = null;
        if (entidad.getProductos() != null) {
            productos = entidad.getProductos().stream()
                .map(sp -> new SolicitudProductoDto(
                    sp.getSolicitud() != null ? sp.getSolicitud().getIdSolicitud() : null,
                    sp.getProducto() != null ? sp.getProducto().getIdProducto() : null,
                    sp.getCantidad()
                )).collect(Collectors.toList());
        }

        List<SolicitudImagenDto> imagenes = null;
        if (entidad.getImagenes() != null) {
            imagenes = entidad.getImagenes().stream()
                .map(si -> new SolicitudImagenDto(
                    si.getIdSolicitudImagen(),
                    si.getSolicitud() != null ? si.getSolicitud().getIdSolicitud() : null,
                    si.getNombre(),
                    si.getExtension(),
                    si.getDescripcion(),
                    si.getUrlImagen(),
                    si.getActivo()
                )).collect(Collectors.toList());
        }

        return new SolicitudDto(
            entidad.getIdSolicitud(),
            entidad.getTipoSolicitud() != null ? entidad.getTipoSolicitud().getIdTipoSolicitud() : null,
            entidad.getEstadoSolicitud() != null ? entidad.getEstadoSolicitud().getIdEstadoSolicitud() : null,
            entidad.getFechaCreacion(),
            entidad.getNombreCliente(),
            entidad.getCorreoCliente(),
            entidad.getTelefonoCliente(),
            entidad.getObservaciones(),
            productos,
            imagenes
        );
    }

    // ---- DTO -> ENTITY ----
    public static Solicitud toEntity(SolicitudDto dto) {
        if (dto == null) return null;
        Solicitud entidad = new Solicitud();
        entidad.setIdSolicitud(dto.getIdSolicitud());
        if (dto.getIdTipoSolicitud() != null) {
            TipoSolicitud tipo = new TipoSolicitud();
            tipo.setIdTipoSolicitud(dto.getIdTipoSolicitud());
            entidad.setTipoSolicitud(tipo);
        }
        if (dto.getIdEstadoSolicitud() != null) {
            EstadoSolicitud estado = new EstadoSolicitud();
            estado.setIdEstadoSolicitud(dto.getIdEstadoSolicitud());
            entidad.setEstadoSolicitud(estado);
        }
        entidad.setFechaCreacion(dto.getFechaCreacion());
        entidad.setNombreCliente(dto.getNombreCliente());
        entidad.setCorreoCliente(dto.getCorreoCliente());
        entidad.setTelefonoCliente(dto.getTelefonoCliente());
        entidad.setObservaciones(dto.getObservaciones());
        return entidad;
    }

    public static TipoSolicitud tipoSolicitudFromId(Long id) {
        if (id == null) return null;
        TipoSolicitud tipo = new TipoSolicitud();
        tipo.setIdTipoSolicitud(id);
        return tipo;
    }
    
    public static EstadoSolicitud estadoSolicitudFromId(Long id) {
        if (id == null) return null;
        EstadoSolicitud estado = new EstadoSolicitud();
        estado.setIdEstadoSolicitud(id);
        return estado;
    }
}
