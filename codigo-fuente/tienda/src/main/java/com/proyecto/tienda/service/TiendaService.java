package com.proyecto.tienda.service;

import com.proyecto.tienda.model.Tienda;
import com.proyecto.tienda.dto.TiendaDTO;
import com.proyecto.tienda.dto.TiendaCreateDTO;
import com.proyecto.tienda.exception.NoEncontradoException;
import com.proyecto.tienda.repository.TiendaRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TiendaService {
    private static final Logger log = LoggerFactory.getLogger(TiendaService.class);

    private final TiendaRepository tiendaRepository;

    public TiendaService (TiendaRepository tiendaRepository){
        this.tiendaRepository = tiendaRepository;
    }

    // 1. Guardar Tienda
    public TiendaDTO guardarTienda(TiendaCreateDTO createDTO){
        log.info("Iniciando el proceso de guardado para la tienda: {}", createDTO.getNombre());
        
        Tienda tiendaEntity = new Tienda();
        tiendaEntity.setNombre(createDTO.getNombre());
        tiendaEntity.setUbicacion(createDTO.getUbicacion());
        tiendaEntity.setHorario(createDTO.getHorario());
        tiendaEntity.setCant_Empleados(createDTO.getCant_Empleados());

        Tienda tiendaGuardada = tiendaRepository.save(tiendaEntity);
        log.info("Tienda guardada exitosamente con el ID asignado: {}", tiendaGuardada.getId());
        
        return convertirATiendaDTO(tiendaGuardada);
    }

    // 2. Validar Tienda Manual
    public List<String> validarTiendaManual(TiendaCreateDTO dto) {
        log.info("Ejecutando validación manual para la tienda: {}", dto.getNombre());
        List<String> errores = new ArrayList<>();

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            errores.add("El nombre no puede estar vacío");
        } else if (dto.getNombre().length() < 2 || dto.getNombre().length() > 50) {
            errores.add("El nombre debe tener entre 2 y 50 caracteres");
        }

        if (dto.getUbicacion() == null || dto.getUbicacion().trim().isEmpty()) {
            errores.add("La ubicación no puede estar vacía");
        }

        if (dto.getHorario() == null || dto.getHorario().trim().isEmpty()) {
            errores.add("El horario no puede estar vacío");
        }

        if (dto.getCant_Empleados() == null) {
            errores.add("La cantidad de empleados no puede ser nula");
        } else if (dto.getCant_Empleados() < 1 || dto.getCant_Empleados() > 100) {
            errores.add("La cantidad de empleados debe ser entre 1 y 100");
        }

        if (!errores.isEmpty()) {
            log.warn("La validación manual falló. Se encontraron {} errores", errores.size());
        } else {
            log.info("Validación manual exitosa. Sin errores encontrados");
        }

        return errores;
    }
    
    // 3. Listar Tiendas
    public List<TiendaDTO> listarTiendas(){
        log.info("Solicitando el listado completo de tiendas desde la base de datos");
        List<Tienda> tiendas = tiendaRepository.findAll();
        log.info("Se recuperaron {} tiendas desde MySQL", tiendas.size());
        
        return tiendas.stream()
                .map(this::convertirATiendaDTO)
                .collect(Collectors.toList());
    }

    // 4. Buscar con ID
    public TiendaDTO buscarConID(Long id){
        log.info("Buscando tienda con ID: {}", id);
        
        Tienda tienda = tiendaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error: No se encontró la tienda con ID: {}", id);
                    return new NoEncontradoException("Tienda no encontrada con el ID: " + id);
                });
                
        log.info("Tienda con ID: {} recuperada con éxito", id);
        return convertirATiendaDTO(tienda);
    }

    // 5. Actualizar Tienda
    public TiendaDTO actualizarTienda (Long id, TiendaCreateDTO detallesTiendaDTO){
        log.info("Iniciando actualización para la tienda con ID: {}", id);
        
        Tienda tiendaExiste = tiendaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error de actualización: Tienda con ID: {} no existe", id);
                    return new NoEncontradoException("No se puede actualizar. Tienda no encontrada con ID: " + id);
                });
        
        tiendaExiste.setNombre(detallesTiendaDTO.getNombre());
        tiendaExiste.setUbicacion(detallesTiendaDTO.getUbicacion());
        tiendaExiste.setHorario(detallesTiendaDTO.getHorario());
        tiendaExiste.setCant_Empleados(detallesTiendaDTO.getCant_Empleados());
        
        Tienda tiendaActualizada = tiendaRepository.save(tiendaExiste);
        log.info("Tienda con ID: {} actualizada correctamente en la base de datos", id);
        
        return convertirATiendaDTO(tiendaActualizada);
    }

    // 6. Quitar Tienda
    public boolean quitarTienda (Long id){
        log.info("Solicitud para eliminar la tienda con ID: {}", id);
        
        if (tiendaRepository.existsById(id)){
            tiendaRepository.deleteById(id);
            log.info("Tienda con ID: {} eliminada físicamente con éxito", id);
            return true;
        }
        
        log.warn("No se pudo eliminar la tienda. El ID: {} no existe", id);
        return false;
    }

    // --- MÉTODO PRIVADO DE CONVERSIÓN INTERNA ---
    private TiendaDTO convertirATiendaDTO(Tienda tienda) {
        TiendaDTO dto = new TiendaDTO();
        dto.setId(tienda.getId());
        dto.setNombre(tienda.getNombre());
        dto.setUbicacion(tienda.getUbicacion());
        dto.setHorario(tienda.getHorario());
        dto.setCant_Empleados(tienda.getCant_Empleados());
        return dto;
    }
}