package com.proyecto.tienda.controller;

import com.proyecto.tienda.dto.TiendaDTO;
import com.proyecto.tienda.dto.TiendaCreateDTO;
import com.proyecto.tienda.service.TiendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tienda", description = "Gestión de tiendas")
@RestController
@RequestMapping("/api/v2/tienda")
public class TiendaController {
    
    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @Operation(summary = "Crear una nueva tienda", description = "Crea una nueva tienda con validación automática de campos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tienda creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida debido a errores de validación")
    })
    @PostMapping("/auto")
    public ResponseEntity<TiendaDTO> crearTienda(@io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody TiendaCreateDTO tiendaCreateDTO) {
        TiendaDTO nuevaTienda = tiendaService.guardarTienda(tiendaCreateDTO);
        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }

    @Operation(summary = "Crear una nueva tienda manualmente", description = "Crea una nueva tienda con validación manual de campos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Solicitud inválida debido a errores de validación")
    })
    @PostMapping("/manual")
    public ResponseEntity<?> crearTiendaManual(@io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody TiendaCreateDTO tiendaCreateDTO) {
        List<String> errores = tiendaService.validarTiendaManual(tiendaCreateDTO);
        if (!errores.isEmpty()) {
            return ResponseEntity.badRequest().body(errores); // Retorna la lista con los mensajes de error
        }
        TiendaDTO nuevaTienda = tiendaService.guardarTienda(tiendaCreateDTO);
        return new ResponseEntity<>(nuevaTienda, HttpStatus.CREATED);
    }

    //Listar todo
    @Operation(summary = "Listar todas las tiendas", description = "Obtiene una lista de todas las tiendas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de tiendas obtenida exitosamente")
    @GetMapping
    public List<TiendaDTO> listarDatos() {
        return tiendaService.listarTiendas();
    }

    @Operation(summary = "Buscar tienda por ID", description = "Obtiene los detalles de una tienda específica utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tienda encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tienda no encontrada con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TiendaDTO> buscarTiendaPorId(@Parameter(description = "ID de la tienda a buscar") @PathVariable Long id) {
        TiendaDTO tiendaDTO = tiendaService.buscarConID(id);
        return ResponseEntity.ok(tiendaDTO);
    }


    @Operation(summary = "Actualizar tienda por ID", description = "Actualiza los detalles de una tienda específica utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tienda actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida debido a errores de validación"),
        @ApiResponse(responseCode = "404", description = "Tienda no encontrada con el ID proporcionado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TiendaDTO> actualizarTienda(@Parameter(description = "ID de la tienda a actualizar") @PathVariable Long id,
                                                      @io.swagger.v3.oas.annotations.parameters.RequestBody @Valid @RequestBody TiendaCreateDTO detallesTiendaDTO) {
        TiendaDTO tiendaActualizada = tiendaService.actualizarTienda(id, detallesTiendaDTO);
        return ResponseEntity.ok(tiendaActualizada);
    }

    @Operation(summary = "Eliminar tienda por ID", description = "Elimina una tienda específica utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tienda eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tienda no encontrada con el ID proporcionado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTienda(@Parameter(description = "ID de la tienda a eliminar") @PathVariable Long id) {
        boolean eliminada = tiendaService.quitarTienda(id);
        if (eliminada) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}