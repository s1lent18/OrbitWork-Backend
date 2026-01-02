package com.example.orbitwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class UpdateSpaceDTO {

    private String name;
    private String description;
    private String googleMapLocation;
    private String city;
    private Integer price;
    private LocalTime openTime;
    private LocalTime closeTime;
}