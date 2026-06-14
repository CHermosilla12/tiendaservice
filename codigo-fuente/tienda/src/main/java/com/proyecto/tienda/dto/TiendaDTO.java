package com.proyecto.tienda.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO para representar los datos de una tienda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaDTO {
    @Schema(description = "ID único de la tienda", example = "1")
    private Long id;
    @Schema(description = "Nombre de la tienda", example = "Tienda Ejemplo")
    private String Nombre;
    @Schema(description = "Ubicación de la tienda", example = "Calle Principal 123")
    private String Ubicacion;
    @Schema(description = "Horario de atención de la tienda", example = "9:00 - 18:00")
    private String Horario;
    @Schema(description = "Cantidad de empleados en la tienda", example = "5")
    private Integer Cant_Empleados;
}