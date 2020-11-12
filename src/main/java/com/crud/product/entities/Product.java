package com.crud.product.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    @NotEmpty(message = "Debe ingresar un nombre de producto")
    @Size(min = 3, message = "El nombre debe contener al menos 3 caracteres.")
    private String productName;

    @Column(name = "product_code")
    @NotEmpty(message ="Debe ingresar un código de producto")
    private String productCode;

    private String description;

    @NotNull(message = "Debe ingresar un precio")
    private BigDecimal price;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "star_rating")
    @DecimalMin(value = "1", message = "El valor debe ser mayor que 1")
    @DecimalMax(value="5", message = "El valor debe ser menor que 5")
    private BigDecimal starRating;

    @Column(name = "image_url")
    private String imageUrl;
}
