package com.microservicio.ms_solicitudes_dyc.service.solicitudProducto;

import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudProducto;
import java.util.List;

public interface SolicitudProductoService {
    List<SolicitudProducto> listarPorIdSolicitud(Long idSolicitud);
    void eliminarPorIdSolicitud(Long idSolicitud);
    void guardar(SolicitudProducto solicitudProducto);
    void guardarTodos(List<SolicitudProducto> productos);
}
