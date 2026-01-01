package com.example.orbitwork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpaceDTO {

    private Long id;

    @NotBlank(message = "Name should not be Empty")
    private String name;

    private String description;

    @NotBlank(message = "Map Link should not be Empty")
    private String googleMapLocation;

    @NotBlank(message = "City should not be Empty")
    private String city;

    @Positive
    @NotNull(message = "A vendor should be associated with the space")
    private Long vendorId;

    @NotBlank(message = "Vendor Name should not be Empty")
    private String vendorName;

    @Positive
    @NotNull(message = "Price should not be null")
    private Integer price;

    @NotNull(message = "Time should not be null")
    private LocalTime openTime;

    @NotNull(message = "Time should not be null")
    private LocalTime closeTime;

    public SpaceDTO(
            String name, String description, String googleMapLocation, String city,
            Long vendorId, String vendorName, Integer price, LocalTime openTime, LocalTime closeTime
    ) {
        this.name = name;
        this.description = description;
        this.googleMapLocation = googleMapLocation;
        this.city = city;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.price = price;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
