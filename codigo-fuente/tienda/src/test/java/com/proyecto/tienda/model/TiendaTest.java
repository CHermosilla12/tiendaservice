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
    @DisplayName("Test getter y setters de Tienda - Debería establecer y obtener correctamente los valores de los atributos")
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

    @Test
    @DisplayName("Test de campo requerido - Debería fallar si el nombre está vacío")
    void testNombreRequeridoDebeFallar() {
        Tienda tienda = new Tienda();
        tienda.setId(1L);
        tienda.setNombre("");
        tienda.setUbicacion("Plaza 789");
        tienda.setHorario("8:00-17:00");
        tienda.setCant_Empleados(15);

        jakarta.validation.ValidatorFactory factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
        jakarta.validation.Validator validator = factory.getValidator();
        var violaciones = validator.validate(tienda);

        assertFalse(violaciones.isEmpty(), "Debería haber una violación de validación por el nombre vacío");
        String mensajeEsperado = "El nombre no puede estar vacío";
        boolean mensajeEncontrado = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals(mensajeEsperado));
        assertTrue(mensajeEncontrado, "Debería contener el mensaje de error: '" + mensajeEsperado + "'");
    }

    @Test
    @DisplayName("Test equals de Tienda - Debería considerar dos tiendas iguales si tienen el mismo id")
    void testEquals() {
        Tienda tienda1 = new Tienda(1L, "Tienda A", "Calle 123", "10:30-20:00", 5);
        Tienda tienda2 = new Tienda(1L, "Tienda A", "Calle 123", "10:30-20:00", 5);

        assertEquals(tienda1, tienda2);
        assertEquals(tienda1.hashCode(), tienda2.hashCode());
    }

    @Test
    @DisplayName("Test toString de Tienda - Debería devolver una representación en cadena de la tienda")
    void testToString() {
        Tienda tienda = new Tienda(3L, "Tienda C", "Plaza 789", "8:00-17:00", 15);

        String esperado = "Tienda(id=3, Nombre=Tienda C, Ubicacion=Plaza 789, Horario=8:00-17:00, Cant_Empleados=15)";

        assertEquals(esperado, tienda.toString());
    }

}
