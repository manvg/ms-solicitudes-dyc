package com.microservicio.ms_solicitudes_dyc.service.solicitudProducto;

import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudProducto;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudProductoServiceImpl implements SolicitudProductoService {

    @Autowired
    private SolicitudProductoRepository solicitudProductoRepository;

    @Override
    public List<SolicitudProducto> listarPorIdSolicitud(Long idSolicitud) {
        return solicitudProductoRepository.findBySolicitud_IdSolicitud(idSolicitud);
    }

    @Override
    public void eliminarPorIdSolicitud(Long idSolicitud) {
        solicitudProductoRepository.deleteBySolicitud_IdSolicitud(idSolicitud);
    }

    @Override
    public void guardar(SolicitudProducto solicitudProducto) {
        solicitudProductoRepository.save(solicitudProducto);
    }

    @Override
    public void guardarTodos(List<SolicitudProducto> productos) {
        solicitudProductoRepository.saveAll(productos);
    }
}
