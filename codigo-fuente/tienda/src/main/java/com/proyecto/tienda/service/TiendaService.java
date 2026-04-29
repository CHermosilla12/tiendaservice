package com.proyecto.tienda.service;

import com.proyecto.tienda.model.Tienda;
import com.proyecto.tienda.repository.TiendaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TiendaService {
    private final TiendaRepository TiendaRepository;

    public TiendaService (TiendaRepository tiendaRepository){
        this.TiendaRepository = tiendaRepository;
    }
public Tienda guardarTienda (Tienda tienda){
    return TiendaRepository.save(tienda);
}
public List <Tienda> buscarDatos () {
    return TiendaRepository.findAll();
}
public Optional <Tienda> buscarConID(Long id){
    return TiendaRepository.findById(id);
}
public Tienda actualizarTienda (Long id, Tienda detallesTienda){
    Optional <Tienda> tiendaExiste = TiendaRepository.findById(id);
    if(tiendaExiste.isPresent()){
        Tienda actualizarTienda = tiendaExiste.get();
               actualizarTienda.setNombre(detallesTienda.getNombre());
               actualizarTienda.setUbicacion(detallesTienda.getUbicacion());
               actualizarTienda.setHorario(detallesTienda.getHorario());
               actualizarTienda.setCant_Empleados(detallesTienda.getCant_Empleados());
            return TiendaRepository.save (actualizarTienda);
    }else{
        return null;
    }
}
public boolean quitarTienda (Long id){
    if (TiendaRepository.existsById(id)){
        TiendaRepository.deleteById(id);
        return true;
    }
    return false;
}

}
