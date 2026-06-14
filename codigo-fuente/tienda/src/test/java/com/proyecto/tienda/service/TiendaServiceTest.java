package com.proyecto.tienda.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.proyecto.tienda.repository.TiendaRepository;
import com.proyecto.tienda.exception.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import com.proyecto.tienda.dto.TiendaDTO;
import com.proyecto.tienda.dto.TiendaCreateDTO;
import com.proyecto.tienda.model.Tienda;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TiendaServiceTest {
    
    @Mock
    private TiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaService tiendaService;

    @Test
    @DisplayName("Test Buscar Todas las Tiendas - Debería retornar una lista de tiendas")
    void testBuscarTodasLasTiendas() {
        List<Tienda> tiendasMock = List.of(
            new Tienda(1L, "Tienda A", "Calle 123", "10:30-20:00", 5),
            new Tienda(2L, "Tienda B", "Avenida 456", "9:00-18:00", 10)
        );
        when(tiendaRepository.findAll()).thenReturn(tiendasMock);


        List<TiendaDTO> tiendas = tiendaService.listarTiendas();

        assertNotNull(tiendas);
        assertEquals(2, tiendas.size());
        assertEquals("Tienda A", tiendas.get(0).getNombre());
        assertEquals(5, tiendas.get(0).getCant_Empleados());
        assertEquals("Tienda B", tiendas.get(1).getNombre());
        assertEquals(10, tiendas.get(1).getCant_Empleados());
        verify(tiendaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Buscar todas las Tiendas - Debería retornar una lista vacía cuando no hay tiendas")
    void testBuscarTodasLasTiendasVacias() {
        when(tiendaRepository.findAll()).thenReturn(List.of());

        List<TiendaDTO> tiendas = tiendaService.listarTiendas();

        assertNotNull(tiendas);
        assertEquals(0, tiendas.size());
        verify(tiendaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Buscar Tienda por ID - Debería retornar la tienda correspondiente")
    void testBuscarTiendaPorId() {
        Tienda tiendaMock = new Tienda(1L, "Tienda A", "Calle 123", "10:30-20:00", 5);
        when(tiendaRepository.findById(1L)).thenReturn(Optional.of(tiendaMock));
        TiendaDTO tienda = tiendaService.buscarConID(1L);
        assertNotNull(tienda);
        assertEquals("Tienda A", tienda.getNombre());
        assertEquals(5, tienda.getCant_Empleados());

    }

    @Test
    @DisplayName("Test Buscar Tienda por ID - Debería lanzar NoEncontradoException cuando no se encuentra la tienda")
    void testBuscarTiendaPorIdNoEncontrada() {
        when(tiendaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoEncontradoException.class, () -> tiendaService.buscarConID(1L));

    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Test Crear Tienda - Debería crear una nueva tienda y retornar el DTO correspondiente")
    void testCrearTienda() {
        TiendaCreateDTO tiendaCreateDTO = new TiendaCreateDTO("Tienda A", "Calle 123", "10:30-20:00", 5);
        Tienda tiendaMock = new Tienda(1L, "Tienda A", "Calle 123", "10:30-20:00", 5);
        when(tiendaRepository.save(any(Tienda.class))).thenReturn(tiendaMock);

        TiendaDTO tienda = tiendaService.guardarTienda(tiendaCreateDTO);

        assertNotNull(tienda);
        assertEquals("Tienda A", tienda.getNombre());
        assertEquals(5, tienda.getCant_Empleados());
        verify(tiendaRepository, times(1)).save(any(Tienda.class));
    }

    @Test
    @DisplayName("Test Eliminar Tienda - Debería eliminar la tienda correspondiente")
    void testEliminarTienda() {
        when(tiendaRepository.existsById(1L)).thenReturn(true);

        tiendaService.quitarTienda(1L);

        verify(tiendaRepository, times(1)).existsById(1L);
    }

}