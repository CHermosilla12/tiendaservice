package com.proyecto.tienda.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.proyecto.tienda.model.Tienda;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DataJpaTest
public class TiendaRepositoryTest {

    @Autowired
    private TiendaRepository repository;

    @Test
    @DisplayName("Test de guardar tienda - debe persistir la tienda y asignar un ID generado automáticamente")
    void persistenciaDeTienda() {
        Tienda tienda = new Tienda(null, "Tienda","Calle 123","8:00-21:00",15);
        Tienda tiendaguardada = repository.save(tienda);

        assertNotNull(tiendaguardada.getId());
        assertTrue(tiendaguardada.getId() > 0);
        assertEquals("Tienda", tiendaguardada.getNombre());
        assertEquals("Calle 123", tiendaguardada.getUbicacion());
        assertEquals("8:00-21:00", tiendaguardada.getHorario());
        assertEquals(15, tiendaguardada.getCant_Empleados());
    }

    @Test
    @DisplayName("Test de encontrar todas las tiendas - Debe retornar una lista con todas las tiendas guardadas")
    void debeRetornarTodasLasTiendas() {

        repository.save(new Tienda(null, "Tienda","Calle 123","8:00-21:00",15));
        repository.save(new Tienda(null, "Tienda 2","Avenida 123","9:00-22:00",35));

        List<Tienda> lista = repository.findAll();

        assertNotNull(lista);
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Test de encontrar por ID - debe retornar la tienda correcta cuando el ID existe")
    void debeEncontrarUnaTiendaPorId() {
        Tienda tiendaguardada = repository.save(new Tienda(null, "Tienda","Calle 123","8:00-21:00",15));
        Long idTienda = tiendaguardada.getId();
        Optional<Tienda> resultado = repository.findById(idTienda);

        assertTrue(resultado.isPresent());
        assertEquals("Tienda", resultado.get().getNombre());
        assertEquals("Calle 123", resultado.get().getUbicacion());
        assertEquals("8:00-21:00", resultado.get().getHorario());
        assertEquals(15, resultado.get().getCant_Empleados());
    }

    @Test
    @DisplayName("Test de encontrar por ID - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarUnOptionalVacioSiNoExiste() {
        Optional<Tienda> resultado = repository.findById(777L);

        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Test de borrado de tineda - debe eliminar la tienda de la base de datos")
    void debeEliminarProductoPorId() {
        // Given
        Tienda guardado = repository.save(new Tienda(null, "Tienda","Calle 123","8:00-21:00",15));
        Long idTienda = guardado.getId();

        repository.deleteById(idTienda);

        assertFalse(repository.findById(idTienda).isPresent());
    }

}
