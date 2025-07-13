package com.microservicio.ms_solicitudes_dyc.service.solicitud;

import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudDto;
import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudImagenDto;
import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudProductoDto;
import com.microservicio.ms_solicitudes_dyc.model.entity.Producto;
import com.microservicio.ms_solicitudes_dyc.model.entity.Solicitud;
import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudBitacora;
import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudImagen;
import com.microservicio.ms_solicitudes_dyc.model.entity.SolicitudProducto;
import com.microservicio.ms_solicitudes_dyc.repository.ProductoRepository;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudBitacoraRepository;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudImagenRepository;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudProductoRepository;
import com.microservicio.ms_solicitudes_dyc.repository.SolicitudRepository;
import com.microservicio.ms_solicitudes_dyc.utilities.SolicitudMapper;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudBitacoraRepository solicitudBitacoraRepository;

    @Autowired
    private SolicitudProductoRepository solicitudProductoRepository;

    @Autowired
    private SolicitudImagenRepository solicitudImagenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    //--------- GET TODOS ---------//
    @Override
    public List<SolicitudDto> obtenerTodos() {
        return solicitudRepository.findAll().stream().map(SolicitudMapper::toDto).collect(Collectors.toList());
    }

    //--------- GET ACTIVOS ---------//
    @Override
    public List<SolicitudDto> obtenerActivos() {
        return solicitudRepository.findByEstadoSolicitud_Activo(1).stream().map(SolicitudMapper::toDto).collect(Collectors.toList());
    }

    //--------- GET POR ID ---------//
    @Override
    public SolicitudDto obtenerPorId(Long id) {
        Optional<Solicitud> optional = solicitudRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Solicitud no encontrada");
        }
        return SolicitudMapper.toDto(optional.get());
    }

    //--------- CREAR ---------//
    @Override
    @Transactional
    public void crear(SolicitudDto dto) {
        //Guardar la solicitud
        Solicitud entidad = SolicitudMapper.toEntity(dto);
        if (entidad.getFechaCreacion() == null) {
            entidad.setFechaCreacion(java.time.LocalDateTime.now());
        }
        entidad = solicitudRepository.save(entidad);

        //Guardar bitácora
        SolicitudBitacora bitacora = new SolicitudBitacora();
        bitacora.setSolicitud(entidad);
        bitacora.setEstadoSolicitud(entidad.getEstadoSolicitud());
        bitacora.setDescripcion("Solicitud creada");
        bitacora.setFechaCreacion(java.time.LocalDateTime.now());
        bitacora.setUsuarioCreacion(dto.getCorreoCliente());
        solicitudBitacoraRepository.save(bitacora);

        //Según el tipo de solicitud, insertar en tablas de solicitud_producto y solicitud_imagen
        Long tipo = dto.getIdTipoSolicitud();
        if (tipo != null) {
            //Solicitud tipo Producto
            if (tipo == 2 && dto.getProductos() != null) {
                for (SolicitudProductoDto prodDto : dto.getProductos()) {
                    SolicitudProducto solProd = new SolicitudProducto();
                    solProd.setSolicitud(entidad);
                    Producto producto = new Producto();
                    producto.setIdProducto(prodDto.getIdProducto());
                    solProd.setProducto(producto);
                    solProd.setCantidad(prodDto.getCantidad());
                    solicitudProductoRepository.save(solProd);
                }
            }
            //Solicitud tipo Personalizada
            if (tipo == 3 && dto.getImagenes() != null) {
                for (SolicitudImagenDto imgDto : dto.getImagenes()) {
                    SolicitudImagen solImg = new SolicitudImagen();
                    solImg.setSolicitud(entidad);
                    solImg.setNombre(imgDto.getNombre());
                    solImg.setExtension(imgDto.getExtension());
                    solImg.setDescripcion(imgDto.getDescripcion());
                    solImg.setUrlImagen(imgDto.getUrlImagen());
                    solImg.setActivo(imgDto.getActivo() != null ? imgDto.getActivo() : 1);
                    solicitudImagenRepository.save(solImg);
                }
            }
        }
    }

    //--------- ACTUALIZAR ---------//
    @Override
    @Transactional
    public void actualizar(Long id, SolicitudDto dto) {
        Solicitud existente = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    
        // Actualizar campos simples
        existente.setTipoSolicitud(
                dto.getIdTipoSolicitud() != null ?
                        SolicitudMapper.tipoSolicitudFromId(dto.getIdTipoSolicitud()) : null);
        existente.setEstadoSolicitud(
                dto.getIdEstadoSolicitud() != null ?
                        SolicitudMapper.estadoSolicitudFromId(dto.getIdEstadoSolicitud()) : null);
    
        existente.setNombreCliente(dto.getNombreCliente());
        existente.setCorreoCliente(dto.getCorreoCliente());
        existente.setTelefonoCliente(dto.getTelefonoCliente());
        existente.setObservaciones(dto.getObservaciones());
    
        //Actualización de productos
        if (existente.getProductos() != null) {
            existente.getProductos().clear();
        }
        if (dto.getProductos() != null) {
            for (SolicitudProductoDto prodDto : dto.getProductos()) {
                SolicitudProducto nuevoProd = new SolicitudProducto();
                nuevoProd.setSolicitud(existente);
                Producto producto = productoRepository.findById(prodDto.getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado (ID: " + prodDto.getIdProducto() + ")"));
                nuevoProd.setProducto(producto);
                nuevoProd.setCantidad(prodDto.getCantidad());
                existente.getProductos().add(nuevoProd);
            }
        }
    
        //Actualización de imagenes
        if (existente.getImagenes() != null) {
            existente.getImagenes().clear();
        }
        if (dto.getImagenes() != null) {
            for (SolicitudImagenDto imgDto : dto.getImagenes()) {
                SolicitudImagen nuevaImagen = new SolicitudImagen();
                nuevaImagen.setSolicitud(existente);
                nuevaImagen.setNombre(imgDto.getNombre());
                nuevaImagen.setExtension(imgDto.getExtension());
                nuevaImagen.setDescripcion(imgDto.getDescripcion());
                nuevaImagen.setUrlImagen(imgDto.getUrlImagen());
                nuevaImagen.setActivo(imgDto.getActivo());
                existente.getImagenes().add(nuevaImagen);
            }
        }
    
        solicitudRepository.save(existente);
    }

    //--------- CAMBIAR ESTADO ACTIVO/INACTIVO ---------//
    @Override
    public void cambiarEstado(Long id, int activo) {
        Solicitud existente = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        existente.getEstadoSolicitud().setActivo(activo);
        solicitudRepository.save(existente);
    }

    //--------- ELIMINAR ---------//
    @Override
    public void eliminar(Long id) {
        if (!solicitudRepository.existsById(id)) {
            throw new RuntimeException("Solicitud no encontrada");
        }
        solicitudRepository.deleteById(id);
    }

}