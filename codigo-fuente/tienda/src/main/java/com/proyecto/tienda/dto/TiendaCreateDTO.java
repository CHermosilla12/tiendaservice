package com.proyecto.tienda.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO para crear una nueva tienda con validación de campos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaCreateDTO {
    
    @Schema(description = "Nombre de la tienda", example = "Tienda Ejemplo")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @Schema(description = "Ubicación de la tienda", example = "Calle Principal 123")
    @NotBlank(message = "La ubicación no puede estar vacía")
    private String ubicacion;

    @Schema(description = "Horario de atención de la tienda", example = "9:00 - 18:00")
    @NotBlank(message = "El horario no puede estar vacío")
    private String horario;

    @Schema(description = "Cantidad de empleados en la tienda", example = "5")
    @Max(value = 100, message = "La cantidad de empleados no puede ser mayor a 100")
    @Min(value = 1, message = "La cantidad de empleados debe ser al menos 1")
    @NotNull(message = "La cantidad de empleados no puede ser nula")
    private Integer cant_Empleados; 
}