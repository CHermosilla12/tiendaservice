package com.proyecto.tienda.service;

import com.proyecto.tienda.model.Tienda;
import com.proyecto.tienda.repository.TiendaRepository;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class TiendaService {
    private final TiendaRepository tiendaRepository;

    public TiendaService (TiendaRepository tiendaRepository){
        this.tiendaRepository = tiendaRepository;
    }
    public Tienda guardarTienda(Tienda tienda){
        return tiendaRepository.save(tienda);
    }

    public List<String> validarTiendaManual(Tienda tienda) {
        List<String> errores = new ArrayList<>();

        if (tienda.getNombre() == null || tienda.getNombre().trim().isEmpty()) {
            errores.add("El nombre no puede estar vacío");
        } else if (tienda.getNombre().length() < 2 || tienda.getNombre().length() > 50) {
            errores.add("El nombre debe tener entre 2 y 50 caracteres");
        }

        if (tienda.getUbicacion() == null || tienda.getUbicacion().trim().isEmpty()) {
            errores.add("La ubicación no puede estar vacía");
        }

        if (tienda.getHorario() == null || tienda.getHorario().trim().isEmpty()) {
            errores.add("El horario no puede estar vacío");
        }

        if (tienda.getCant_Empleados() == null) {
            errores.add("La cantidad de empleados no puede ser nula");
        } else if (tienda.getCant_Empleados() < 1 || tienda.getCant_Empleados() > 100) {
            errores.add("La cantidad de empleados debe ser entre 1 y 100");
        }

        return errores;
    }
    
    public List<Tienda> listarTiendas(){
        return tiendaRepository.findAll();
    }
    public Optional<Tienda> buscarConID(Long id){
        return tiendaRepository.findById(id);
    }
    public Tienda actualizarTienda (Long id, Tienda detallesTienda){
        Optional <Tienda> tiendaExiste = tiendaRepository.findById(id);
        if(tiendaExiste.isPresent()){
            Tienda actualizarTienda = tiendaExiste.get();
               actualizarTienda.setNombre(detallesTienda.getNombre());
               actualizarTienda.setUbicacion(detallesTienda.getUbicacion());
               actualizarTienda.setHorario(detallesTienda.getHorario());
               actualizarTienda.setCant_Empleados(detallesTienda.getCant_Empleados());
            return tiendaRepository.save (actualizarTienda);
        }else{
            return null;
        }
    }
    public boolean quitarTienda (Long id){
        if (tiendaRepository.existsById(id)){
            tiendaRepository.deleteById(id);
            return true;
        }
        return false;
}

}
