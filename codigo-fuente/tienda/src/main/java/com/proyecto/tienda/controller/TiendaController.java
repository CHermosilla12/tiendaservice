package com.proyecto.tienda.controller;

import com.proyecto.tienda.model.Tienda;
import com.proyecto.tienda.service.TiendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tienda")
public class TiendaController {
    
    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @PostMapping("/auto")
    public ResponseEntity<Tienda> crearTienda(@Valid @RequestBody Tienda tienda) {
        Tienda nuevaTienda = tiendaService.guardarTienda(tienda);
        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }

    @PostMapping("/manual")
    public ResponseEntity<Tienda> crearTiendaManual(@Valid @RequestBody Tienda tienda) {
        List<String> errores = tiendaService.validarTiendaManual(tienda);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Tienda nuevaTienda = tiendaService.guardarTienda(tienda);
        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Tienda> listarDatos() {
        return tiendaService.listarTiendas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tienda> buscarTiendaPorId(@PathVariable Long id) {
        Optional<Tienda> tienda = tiendaService.buscarConID(id);
        if (tienda.isPresent()) {
            return ResponseEntity.ok(tienda.get());
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tienda> actualizarTienda(@PathVariable Long id, @Valid @RequestBody Tienda detallesTienda) {
        Tienda tiendaActualizada = tiendaService.actualizarTienda(id, detallesTienda);
        if (tiendaActualizada != null) {
            return ResponseEntity.ok(tiendaActualizada);
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTienda(@PathVariable Long id) {
        boolean eliminada = tiendaService.quitarTienda(id);
        if (eliminada) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
