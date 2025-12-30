package com.example.orbitwork.dto;

import com.example.orbitwork.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Data
public class SpaceDTO {

    private Long id;

    private String name;

    private String description;

    private String googleMapLocation;

    private String city;

    private User vendor;

    private Integer price;

    private LocalTime openTime;

    private LocalTime closeTime;

    public SpaceDTO(
            String name, String description, String googleMapLocation, String city,
            User vendor, Integer price, LocalTime openTime, LocalTime closeTime
    ) {
        this.name = name;
        this.description = description;
        this.googleMapLocation = googleMapLocation;
        this.city = city;
        this.vendor = vendor;
        this.price = price;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
