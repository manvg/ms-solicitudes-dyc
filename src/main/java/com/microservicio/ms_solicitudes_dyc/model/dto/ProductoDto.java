package com.microservicio.ms_solicitudes_dyc.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {
    private Long idProducto;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    // @NotBlank(message = "El material no puede estar vacío")
    // private String material;

    @NotBlank(message = "Las medidas no pueden estar vacías")
    private String medidas;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    private String urlImagen;

    @Min(value = 0)
    @Max(value = 1)
    private Integer activo;

    @NotNull(message = "Debe indicar el tipo de producto")
    private Long idTipoProducto;

    private String nombreTipoProducto;

    @NotNull(message = "Debe indicar el material") //new 30/06/25
    private Long idMaterial;

    //opt
    private String nombreMaterial; //new 30/06/25
}