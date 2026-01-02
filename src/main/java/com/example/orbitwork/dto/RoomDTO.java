package com.example.orbitwork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDTO {

    private Long id;

    @NotBlank(message = "Name must not be empty")
    private String name;

    @Positive
    @NotNull(message = "capacity should not be empty")
    private Integer capacity;

    @Positive
    @NotNull(message = "price should not be empty")
    private Integer price;
}
