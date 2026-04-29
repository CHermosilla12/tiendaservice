package com.proyecto.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String Nombre;

    @NotBlank(message = "La ubicación no puede estar vacía")
    private String Ubicacion;

    @NotBlank(message = "El horario no puede estar vacío")
    private String Horario;

    @Max(value = 100, message = "La cantidad de empleados no puede ser mayor a 100")
    @Min(value = 1, message = "La cantidad de empleados debe ser al menos 1")
    @NotNull(message = "La cantidad de empleados no puede ser nula")
    private Integer Cant_Empleados;
}
