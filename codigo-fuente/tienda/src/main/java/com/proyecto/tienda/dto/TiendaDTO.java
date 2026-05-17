package com.proyecto.tienda.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaDTO {
    
    private Long id;
    private String nombre;
    private String ubicacion;
    private String horario;
    private Integer cant_Empleados;
}
