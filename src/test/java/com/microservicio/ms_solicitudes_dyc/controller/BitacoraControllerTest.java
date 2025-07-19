package com.microservicio.ms_solicitudes_dyc.controller;

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
import com.microservicio.ms_solicitudes_dyc.model.dto.SolicitudBitacoraDto;
import com.microservicio.ms_solicitudes_dyc.service.bitacora.SolicitudBitacoraService;

@WebMvcTest(BitacoraController.class)
public class BitacoraControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SolicitudBitacoraService bitacoraService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("GET /api/solicitudes/bitacora/{idSolicitud} → 200 + lista vacía")
    void testObtenerPorSolicitud() throws Exception {
        when(bitacoraService.obtenerPorSolicitud(1L)).thenReturn(List.of());
        mvc.perform(get("/api/solicitudes/bitacora/1"))
           .andExpect(status().isOk())
           .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("POST /api/solicitudes/bitacora → 200 + mensaje creado")
    void testCrearBitacora() throws Exception {
        SolicitudBitacoraDto dtoIn = new SolicitudBitacoraDto();
        dtoIn.setDescripcion("Nueva entrada");

        Mockito.doNothing().when(bitacoraService).crear(any(SolicitudBitacoraDto.class));

        mvc.perform(post("/api/solicitudes/bitacora")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoIn)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Bitácora creada correctamente"));

        ArgumentCaptor<SolicitudBitacoraDto> captor = ArgumentCaptor.forClass(SolicitudBitacoraDto.class);
        verify(bitacoraService).crear(captor.capture());
        assert "Nueva entrada".equals(captor.getValue().getDescripcion());
    }

    @Test
    @DisplayName("PUT /api/solicitudes/bitacora/{idBitacora} → 200 + mensaje actualizado")
    void testActualizarBitacora() throws Exception {
        SolicitudBitacoraDto dtoIn = new SolicitudBitacoraDto();
        dtoIn.setDescripcion("Modificado");

        Mockito.doNothing().when(bitacoraService).actualizar(Mockito.eq(1L), any(SolicitudBitacoraDto.class));

        mvc.perform(put("/api/solicitudes/bitacora/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoIn)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Bitácora actualizada correctamente"));
    }

    @Test
    @DisplayName("DELETE /api/solicitudes/bitacora/{idBitacora} → 200 + mensaje eliminado")
    void testEliminarBitacora() throws Exception {
        Mockito.doNothing().when(bitacoraService).eliminar(1L);

        mvc.perform(delete("/api/solicitudes/bitacora/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value(true))
           .andExpect(jsonPath("$.message").value("Bitácora eliminada correctamente"));
    }
}
