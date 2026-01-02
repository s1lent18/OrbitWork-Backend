package com.example.orbitwork.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Positive
    @Column(nullable = false)
    private Integer capacity;

    @Positive
    @Column(nullable = false)
    private Integer price;

    @Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    public Room(String name, Integer capacity, Integer price, LocalDateTime createdAt) {
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.createdAt = createdAt;
    }
}