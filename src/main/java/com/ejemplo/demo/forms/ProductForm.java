package com.ejemplo.demo.forms;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductForm {

    @NotEmpty(message = "El nombre del producto es requerido")
    private String name;

    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double price;

    @NotEmpty(message = "La URL de la imagen es requerida")
    private String image;

    @NotEmpty(message = "La descripción es requerida")
    private String description;

    // ✅ Constructor vacío (requerido para los formularios de Spring)
    public ProductForm() {
    }

    // ✅ Constructor con todos los atributos
    public ProductForm(String name, Double price, String image, String description) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
    }

    // ✅ Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
