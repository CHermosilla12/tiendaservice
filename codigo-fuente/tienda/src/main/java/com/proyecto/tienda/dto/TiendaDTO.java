package com.proyecto.tienda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaDTO {
    
    private Long id;
    private String Nombre;
    private String Ubicacion;
    private String Horario;
    private Integer Cant_Empleados;
}