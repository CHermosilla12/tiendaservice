package com.proyecto.tienda.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.proyecto.tienda.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import com.proyecto.tienda.exception.*;
import com.proyecto.tienda.service.TiendaService;
import org.springframework.http.MediaType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TiendaControllerTest {

    @Mock
    private TiendaService service;

    @InjectMocks
    private TiendaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    //GET
    @Test
    @DisplayName("GET /api/v2/tienda - debe retornar 200 con la lista de Tiendas")
    void debeRetornar200CuandoSePidenLasTiendas() throws Exception {
        // Given
        when(service.listarTiendas()).thenReturn(List.of(
            new TiendaDTO(null, "Tienda", "Calle 123", "8:00-21:00", 15),
            new TiendaDTO(null, "Tienda 2", "Avenida 123", "9:00-22:00", 35)
        ));

        // When & Then - Asegura la concordancia con el retorno mapeado en TiendaDTO
        mockMvc.perform(get("/api/v2/tienda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Tienda")) 
                .andExpect(jsonPath("$[0].cant_Empleados").value(15));
    }

    @Test
    @DisplayName("GET /api/v2/tienda - debe retornar 200 con lista vacía cuando no hay registros")
    void debeRetornar200ConListaVacia() throws Exception {
        when(service.listarTiendas()).thenReturn(List.of());

        mockMvc.perform(get("/api/v2/tienda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    //GET /{Id}
    @Test
    @DisplayName("GET /api/v2/tienda/{id} - debe retornar 404 cuando la tienda no existe")
    void debeRetornar404CuandoTiendaNoExiste() throws Exception {
        // Given
        when(service.buscarConID(999L))
                .thenThrow(new NoEncontradoException("Tienda no encontrada con ID: 999"));

        // When & Then
        mockMvc.perform(get("/api/v2/tienda/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso no encontrado")); //
    }

    @Test
    @DisplayName("POST /api/v2/tienda - debe retornar 201 al crear una tienda válida")
    void debeDevolver201AlCrearTienda() throws Exception {
        // Given
        String json = """
                {
                    "nombre": "Tienda",
                    "ubicacion": "Calle 123",
                    "horario": "8:00-21:00",
                    "cant_Empleados": 15
                }
                """; //
                
        when(service.guardarTienda(any())).thenReturn(
                new TiendaDTO(null, "Tienda", "Calle 123", "8:00-21:00", 15));

        // When & Then
        mockMvc.perform(post("/api/v2/tienda/auto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Tienda")); 
    }

    //Post
    @Test
    @DisplayName("POST /api/v2/tienda - debe retornar 400 cuando el nombre está en blanco")
    void debeRetornar400CuandoNombreEstaVacio() throws Exception {
        String json = """
                {
                    "nombre": "",
                    "ubicacion": "Calle 123",
                    "horario": "8:00-21:00",
                    "cant_Empleados": -15
                }
                """;

        // When & Then
        mockMvc.perform(post("/api/v2/tienda/auto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}