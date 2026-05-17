package com.proyecto.tienda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiendaCreateDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 5, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "La ubicación no puede estar vacía")
    private String ubicacion;

    @NotBlank(message = "El horario no puede estar vacío")
    private String horario;

    @NotNull(message = "La cantidad de empleados no puede ser nula")
    @Min(value = 1, message = "La cantidad de empleados debe ser al menos 1")
    @Max(value = 100, message = "La cantidad de empleados no puede ser mayor a 100")
    private Integer cant_Empleados;
}