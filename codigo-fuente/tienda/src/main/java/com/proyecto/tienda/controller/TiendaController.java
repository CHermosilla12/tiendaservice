package com.proyecto.tienda.controller;

import com.proyecto.tienda.dto.TiendaDTO;
import com.proyecto.tienda.dto.TiendaCreateDTO;
import com.proyecto.tienda.service.TiendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tienda")
public class TiendaController {
    
    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @PostMapping("/auto")
    public ResponseEntity<TiendaDTO> crearTienda(@Valid @RequestBody TiendaCreateDTO tiendaCreateDTO) {
        TiendaDTO nuevaTienda = tiendaService.guardarTienda(tiendaCreateDTO);
        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }

    @PostMapping("/manual")
    public ResponseEntity<?> crearTiendaManual(@RequestBody TiendaCreateDTO tiendaCreateDTO) {
        List<String> errores = tiendaService.validarTiendaManual(tiendaCreateDTO);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores); // Retorna la lista con los mensajes de error
        }
        TiendaDTO nuevaTienda = tiendaService.guardarTienda(tiendaCreateDTO);
        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }

    //Listar todo
    @GetMapping
    public List<TiendaDTO> listarDatos() {
        return tiendaService.listarTiendas();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TiendaDTO> buscarTiendaPorId(@PathVariable Long id) {
        TiendaDTO tiendaDTO = tiendaService.buscarConID(id);
        return ResponseEntity.ok(tiendaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TiendaDTO> actualizarTienda(@PathVariable Long id, @Valid @RequestBody TiendaCreateDTO detallesTiendaDTO) {
        TiendaDTO tiendaActualizada = tiendaService.actualizarTienda(id, detallesTiendaDTO);
        return ResponseEntity.ok(tiendaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTienda(@PathVariable Long id) {
        boolean eliminada = tiendaService.quitarTienda(id);
        if (eliminada) {
            return ResponseEntity.noContent().build(); // Código 204 No Content (Estándar para DELETE exitosos)
        } else {
            return ResponseEntity.notFound().build(); // Código 404 si intentan borrar algo que ya no existe
        }
    }
}