package com.proyecto.tienda.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;


class TiendaTest {
    @Test
    @DisplayName("Test constructor vacio de Tienda - Debería crear una instancia no nula")
    void testConstructorVacio() {
        Tienda tienda = new Tienda();
        assertNotNull(tienda);
        
    }    

    @Test
    @DisplayName("Test constructor con parámetros de Tienda - Debería crear una instancia con los valores proporcionados")
    void testConstructorConParametros() {
        Tienda tienda = new Tienda(1L, "Tienda A", "Calle 123", "10:30-20:00", 5);
        
        assertEquals(1L, tienda.getId());
        assertEquals("Tienda A", tienda.getNombre());
        assertEquals("Calle 123", tienda.getUbicacion());
        assertEquals("10:30-20:00", tienda.getHorario());
        assertEquals(5, tienda.getCant_Empleados());
    }

    @Test
    @DisplayName("Test setters de Tienda - Deberían establecer los valores correctamente")
    void testSetters() {
        Tienda tienda = new Tienda();
        tienda.setId(2L);
        tienda.setNombre("Tienda B");
        tienda.setUbicacion("Avenida 456");
        tienda.setHorario("9:00-18:00");
        tienda.setCant_Empleados(10);

        assertEquals(2L, tienda.getId());
        assertEquals("Tienda B", tienda.getNombre());
        assertEquals("Avenida 456", tienda.getUbicacion());
        assertEquals("9:00-18:00", tienda.getHorario());
        assertEquals(10, tienda.getCant_Empleados());
    }

}
