package com.microservicio.ms_solicitudes_dyc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudDto;
import com.microservicio.ms_solicitudes_dyc.service.solicitud.SolicitudService;
import com.microservicio.ms_solicitudes_dyc.repository.EstadoSolicitudRepository;
import com.microservicio.ms_solicitudes_dyc.utilities.MensajesSolicitud;

@WebMvcTest(SolicitudController.class)
public class SolicitudControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SolicitudService solicitudService;

    @MockBean
    private EstadoSolicitudRepository estadoSolicitudRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("GET /api/solicitudes → 200 + lista vacía")
    void testObtenerTodos() throws Exception {
        when(solicitudService.obtenerTodos()).thenReturn(List.of());
        mvc.perform(get("/api/solicitudes"))
           .andExpect(status().isOk())
           .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("GET /api/solicitudes/{id} → 200 + solicitud encontrada")
    void testObtenerPorId() throws Exception {
        SolicitudDto dto = new SolicitudDto();
        dto.setIdSolicitud(1L);
        when(solicitudService.obtenerPorId(1L)).thenReturn(dto);

        mvc.perform(get("/api/solicitudes/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.idSolicitud").value(1L));
    }

    @Test
    @DisplayName("GET /api/solicitudes/estados → 200 + lista vacía")
    void testObtenerEstados() throws Exception {
        when(estadoSolicitudRepository.findAll()).thenReturn(List.of());

        mvc.perform(get("/api/solicitudes/estados"))
           .andExpect(status().isOk())
           .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("POST /api/solicitudes → 200 + mensaje creado")
    void testCrearSolicitud() throws Exception {
        SolicitudDto dtoIn = new SolicitudDto();
        dtoIn.setNombreCliente("Juan");

        Mockito.doNothing().when(solicitudService).crear(any(SolicitudDto.class));

        mvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoIn)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value(MensajesSolicitud.CREADO));

        ArgumentCaptor<SolicitudDto> captor = ArgumentCaptor.forClass(SolicitudDto.class);
        verify(solicitudService).crear(captor.capture());
        assertEquals("Juan", captor.getValue().getNombreCliente());
    }

    @Test
    @DisplayName("PUT /api/solicitudes/{id} → 200 + mensaje actualizado")
    void testActualizarSolicitud() throws Exception {
        SolicitudDto dtoIn = new SolicitudDto();
        dtoIn.setNombreCliente("Maria");
        
        Mockito.doNothing().when(solicitudService).actualizar(Mockito.eq(1L), any(SolicitudDto.class));

        mvc.perform(put("/api/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoIn)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value(MensajesSolicitud.ACTUALIZADO));
    }

    @Test
    @DisplayName("DELETE /api/solicitudes/{id} → 200 + mensaje eliminado")
    void testEliminarSolicitud() throws Exception {
        Mockito.doNothing().when(solicitudService).eliminar(1L);

        mvc.perform(delete("/api/solicitudes/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value(MensajesSolicitud.ELIMINADO));
    }
}
